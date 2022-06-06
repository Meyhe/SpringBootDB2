package by.alina.SpringBootDB.controller;

import by.alina.SpringBootDB.exceptions.PersonNotFoundException;
import by.alina.SpringBootDB.model.Person;
import by.alina.SpringBootDB.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ItemController {

    public static final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final PersonService personService;

    @Autowired
    public ItemController(PersonService personService){
        this.personService = personService;
    }

    @GetMapping("/people")
    public ResponseEntity<List<Person>> getPeople(){
        List<Person> people = personService.getPeople();
        return people.isEmpty()
                ? new ResponseEntity(HttpStatus.NO_CONTENT)
                : new ResponseEntity<List<Person>>(people, HttpStatus.OK);
    }

    @GetMapping("/people/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable int id){
        Person person = personService.getPerson(id);
            return person == null
                    ? new ResponseEntity(new PersonNotFoundException("User with id = " + id + " - not found"), HttpStatus.NOT_FOUND)
                    : new ResponseEntity<Person>(person, HttpStatus.OK);
    }

    @DeleteMapping("/people/{id}")
    public Person deletePerson(@PathVariable int id){
        return personService.deletePerson(id);
    }

    @PostMapping("/people")
    public ResponseEntity<?> addPerson(@RequestBody Person person, UriComponentsBuilder ucBuilder){
        personService.addPerson(person);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("api/people/{id}").buildAndExpand(person.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/people/{id}")
    public Person updatePerson(@PathVariable int id, @RequestBody Person person){
        return personService.updatePerson(id, person);
    }
}
