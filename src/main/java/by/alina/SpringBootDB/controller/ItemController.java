package by.alina.SpringBootDB.controller;

import by.alina.SpringBootDB.model.Person;
import by.alina.SpringBootDB.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {
    private PersonService personService;

    @Autowired
    public ItemController(PersonService personService){
        this.personService = personService;
    }

    @GetMapping()
    public List<Person> getPeople(){
        return personService.getPeople();
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable int id){
        return personService.getPerson(id);
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable int id){
        personService.deletePerson(id);
        return "The record was successfully deleted";
    }

    @PostMapping()
    public String addPerson(@RequestBody Person person){
        personService.addPerson(person);
        return "The record was successfully added";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable int id, @RequestBody Person person){
        personService.updatePerson(id, person);
        return "The record was successfully updated";
    }
}
