package by.alina.SpringBootDB.controller;

import by.alina.SpringBootDB.model.Person;
import by.alina.SpringBootDB.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ItemController {

    private final PersonService personService;

    @Autowired
    public ItemController(PersonService personService){
        this.personService = personService;
    }

    @GetMapping("/people")
    public ResponseEntity<List<Person>> getPeople(){
       final List<Person> people = personService.getPeople();
        return (!people.isEmpty() && people != null)
                ? new ResponseEntity<>(people,HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/people/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable int id){
        final Person person = personService.getPerson(id);
            return person != null
                    ? new ResponseEntity<>(person, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/people/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable int id){
        final Person person = personService.getPerson(id);
        final int numDeletedRows = personService.deletePerson(id);
        return numDeletedRows != 0
                ? new ResponseEntity<>(person, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/people")
    public ResponseEntity<?> addPerson(@RequestBody Person person){
        personService.addPerson(person);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @PutMapping("/people/{id}")
    public ResponseEntity<?> updatePerson(@PathVariable int id, @RequestBody Person person){
        final int numUpdatedRows = personService.updatePerson(id, person);
        return numUpdatedRows != 0
                ? new ResponseEntity<>(person, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
