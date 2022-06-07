package by.alina.SpringBootDB.dao;

import by.alina.SpringBootDB.mapper.PersonRowMapper;
import by.alina.SpringBootDB.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;
    private final PersonRowMapper personRowMapper;


    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate, PersonRowMapper personRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.personRowMapper = personRowMapper;
    }

    public Person getPerson(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", personRowMapper, id)
                .stream()
                .findAny()
                .orElse(null);
    }

    public List<Person> getPeople(){
        return jdbcTemplate.query("SELECT * FROM Person", personRowMapper);
    }

    public Person addPerson(Person person){
        jdbcTemplate.update("INSERT INTO Person(name, age) VALUES(?, ?)", person.getName(), person.getAge());
        return person;
    }

    public int updatePerson(int id, Person person){
        return jdbcTemplate.update("UPDATE Person SET name=?, age=? WHERE id=?", person.getName(), person.getAge(), id);
    }

    public int deletePerson(int id){
        return jdbcTemplate.update("DELETE FROM Person WHERE id = ?", id);
    }
}
