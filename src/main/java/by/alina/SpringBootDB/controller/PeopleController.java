package by.alina.SpringBootDB.controller;

import by.alina.SpringBootDB.model.Person;
import by.alina.SpringBootDB.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class PeopleController {

    @Autowired
    private PersonService personService;

    @GetMapping("/{id}")
    public String getPerson(@PathVariable int id, Model model){
        model.addAttribute("person",personService.getPerson(id));
        return "people/person";
    }

    @GetMapping()
    public String getPeople(Model model){
        model.addAttribute("people", personService.getPeople());
        return "people/people";
    }

    @GetMapping("/update/{id}")
    public String updatePerson(@PathVariable int id, Model model){
        model.addAttribute("person", personService.getPerson(id));
        return "people/update";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult result,
                         @PathVariable int id){
        if (result.hasErrors()){
            return "people/update";
        }
        personService.updatePerson(id, person);
        return "redirect:/people";
    }

    @GetMapping("/add")
    public String addPage(@ModelAttribute("person")Person person){
        return "people/add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String create(@Valid Person person, BindingResult result){
        if (result.hasErrors()){
            return "people/add";
        }
        personService.addPerson(person);
        return "redirect:/people";
    }

    @RequestMapping("/delete/{id}")
    public String deletePerson(@PathVariable int id){
        personService.deletePerson(id);
        return "redirect:/people";
    }
}
