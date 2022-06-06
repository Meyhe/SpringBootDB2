package by.alina.SpringBootDB.service;

import by.alina.SpringBootDB.dao.PersonDAO;
import by.alina.SpringBootDB.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final PersonDAO personDAO;

    @Autowired
    public PersonService(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public Person getPerson(int id){
        return personDAO.getPerson(id);
    }

    public List<Person> getPeople(){
        return personDAO.getPeople();
    }

    public Person addPerson(Person person){
        return personDAO.addPerson(person);
    }

    public Person updatePerson(int id, Person person){
        return personDAO.updatePerson(id, person);
    }

    public Person deletePerson(int id){
        return personDAO.deletePerson(id);
    }
}
