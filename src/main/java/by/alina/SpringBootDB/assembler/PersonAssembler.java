package by.alina.SpringBootDB.assembler;

import by.alina.SpringBootDB.controller.PersonController;
import by.alina.SpringBootDB.dto.PersonDTO;
import by.alina.SpringBootDB.mapper.PersonMapper;
import by.alina.SpringBootDB.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PersonAssembler implements RepresentationModelAssembler<Person, PersonDTO> {

    private PersonMapper personMapper;

    @Autowired
    public PersonAssembler(PersonMapper personMapper)
    {
        this.personMapper = personMapper;
    }

    @Override
    public PersonDTO toModel(Person person) {

        PersonDTO personDTO = personMapper.personToPersonDTO(person);

        personDTO.add(linkTo(methodOn(PersonController.class)
                .getPerson(person.getId()))
                .withRel("PRODUCTS list")
                .withType("GET"));

        personDTO.add(linkTo(methodOn(PersonController.class)
                .updatePerson(person, person.getId()))
                .withRel("Update PERSON")
                .withType("PUT"));

        personDTO.add(linkTo(methodOn(PersonController.class)
                .deletePerson(person.getId()))
                .withRel("Delete PERSON")
                .withType("DELETE"));

        return personDTO;
    }

    @Override
    public CollectionModel<PersonDTO> toCollectionModel(Iterable<? extends Person> personList) {
        CollectionModel<PersonDTO> personDTOCollectionModel = RepresentationModelAssembler.super.toCollectionModel(personList);
        return personDTOCollectionModel;
    }
}
