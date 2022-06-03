package by.alina.SpringBootDB.dao;

import by.alina.SpringBootDB.mapper.PersonRowMapper;
import by.alina.SpringBootDB.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private PersonRowMapper personRowMapper = new PersonRowMapper();

    public Person getPerson(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", personRowMapper, id)
                .stream().findAny().orElse(null);
    }

    public List<Person> getPeople(){
        return jdbcTemplate.query("SELECT * FROM Person", personRowMapper);
    }

    public void addPerson(Person person){
        jdbcTemplate.update("INSERT INTO Person(name, age) VALUES(?, ?)", person.getName(), person.getAge());
    }

    public void updatePerson(int id, Person person){
        jdbcTemplate.update("UPDATE Person SET name=?, age=? WHERE id=?", person.getName(), person.getAge(), id);
    }

    public void deletePerson(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id = ?", id);
    }
}
