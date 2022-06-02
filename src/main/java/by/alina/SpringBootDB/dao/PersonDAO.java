package by.alina.SpringBootDB.dao;

import by.alina.SpringBootDB.mapper.PersonRowMapper;
import by.alina.SpringBootDB.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class PersonDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Person getPerson(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public List<Person> getPeople(){
        return jdbcTemplate.query("SELECT * FROM Person", new PersonRowMapper());
    }

    public void addPerson(Person person){
        jdbcTemplate.update("INSERT INTO Person(name, age) VALUES(?, ?)", new Object[]{person.getName(), person.getAge()});
    }

    public void updatePerson(int id, Person person){
        jdbcTemplate.update("UPDATE Person SET name=?, age=? WHERE id=?", person.getName(), person.getAge(), id);
    }

    public void deletePerson(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id = ?", new Object[]{id});
    }
}
