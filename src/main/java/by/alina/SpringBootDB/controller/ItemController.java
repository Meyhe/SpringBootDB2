package by.alina.SpringBootDB.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import by.alina.SpringBootDB.exception.MyEntityNotFoundException;
import by.alina.SpringBootDB.model.Person;
import by.alina.SpringBootDB.repository.PersonRepository;
import org.apache.tomcat.jni.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/people")
public class ItemController {

    private final PersonRepository personRepository;

    @Autowired
    public ItemController(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    @GetMapping
    public ResponseEntity<List<Person>> getPeople() {
        List<Person> personList = (List<Person>) personRepository.findAll();

        for (Person person : personList){
            Long personId = person.getId();

            person.add(linkTo(methodOn(ItemController.class)
                    .getPerson(personId))
                    .withRel("get_person")
                    .withType("GET"));

            person.add(linkTo(methodOn(ItemController.class)
                    .deletePerson(personId))
                    .withRel("delete_person")
                    .withType("DELETE"));
        }
        return ResponseEntity.ok().body(personList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id){
        Optional<Person> person = personRepository.findById(id);

        if (!person.isPresent()){
            throw new MyEntityNotFoundException(id);
        } else {
            return ResponseEntity.ok().body(person.get());
        }
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person){
        Person newPerson = personRepository.save(person);

        return ResponseEntity.status(HttpStatus.CREATED).body(newPerson);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person, @PathVariable Long id){
        Optional<Person> updatePer = personRepository.findById(id);

        if (!updatePer.isPresent()){
            throw new MyEntityNotFoundException(id);
        } else {
            Person p = updatePer.get();
            p.setName(person.getName());
            p.setAge(person.getAge());
            return ResponseEntity.ok().body(personRepository.save(p));
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable Long id) {
        Optional<Person> person = personRepository.findById(id);

        if(!person.isPresent()){
            throw  new MyEntityNotFoundException(id);
        } else {
            personRepository.deleteById(id);
            person.get().add(linkTo(methodOn(ItemController.class).getPeople()).withRel("get_people").withType("GET"));
            return ResponseEntity.ok().body(person.get());
        }
    }
}
