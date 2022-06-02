package by.alina.SpringBootDB.service;

import by.alina.SpringBootDB.dao.PersonDAO;
import by.alina.SpringBootDB.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonDAO personDAO;

    public Person getPerson(int id){
        return personDAO.getPerson(id);
    }

    public List<Person> getPeople(){
        return personDAO.getPeople();
    }

    public void addPerson(Person person){
        personDAO.addPerson(person);
    }

    public void updatePerson(int id, Person person){
        personDAO.updatePerson(id, person);
    }

    public void deletePerson(int id){
        personDAO.deletePerson(id);
    }
}
