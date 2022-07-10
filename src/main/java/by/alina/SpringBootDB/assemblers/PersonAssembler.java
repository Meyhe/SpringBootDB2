package by.alina.SpringBootDB.assemblers;

import by.alina.SpringBootDB.controllers.PersonController;
import by.alina.SpringBootDB.dtos.PersonDTO;
import by.alina.SpringBootDB.models.Person;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PersonAssembler implements RepresentationModelAssembler<Person, PersonDTO> {

    @Override
    public PersonDTO toModel(Person person) {

        PersonDTO personDTO = new PersonDTO(person.getId(), person.getName(), person.getAge());

        personDTO.setId(person.getId());
        personDTO.setName(person.getName());
        personDTO.setAge(person.getAge());

        personDTO.add(linkTo(methodOn(PersonController.class)
                .getPerson(person.getId()))
                .withRel("Person's details")
                .withType("GET"));

        personDTO.add(linkTo(methodOn(PersonController.class)
                .updatePerson(person, person.getId()))
                .withRel("Update person")
                .withType("PUT"));

        personDTO.add(linkTo(methodOn(PersonController.class)
                .deletePerson(person.getId()))
                .withRel("Delete person")
                .withType("DELETE"));

        personDTO.add(linkTo(methodOn(PersonController.class)
                .getPeople())
                .withRel("Get all people")
                .withType("GET"));

        return personDTO;
    }
}
