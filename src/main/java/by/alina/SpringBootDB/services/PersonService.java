package by.alina.SpringBootDB.services;

import by.alina.SpringBootDB.assemblers.ProductAssembler;
import by.alina.SpringBootDB.exceptions.MyEntityNotFoundException;
import by.alina.SpringBootDB.models.Person;
import by.alina.SpringBootDB.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final ProductAssembler productAssembler;

    @Autowired
    public PersonService(PersonRepository personRepository, ProductAssembler productAssembler) {
        this.personRepository = personRepository;
        this.productAssembler = productAssembler;
    }

    public Person getPerson(Long id){
        return personRepository.findById(id).orElseThrow(()-> new MyEntityNotFoundException(id));
    }

    public List<Person> getPeople(){
        return (List<Person>) personRepository.findAll();
    }

    public Person addPerson(Person person){
        return personRepository.save(person);
    }

    public Person updatePerson(Person person, Long id){
        Person updatePerson = getPerson(id);

        updatePerson.setName(person.getName());
        updatePerson.setAge(person.getAge());
        return personRepository.save(updatePerson);
    }

    public void deletePerson(Long id){
        personRepository.deleteById(id);
    }
}
