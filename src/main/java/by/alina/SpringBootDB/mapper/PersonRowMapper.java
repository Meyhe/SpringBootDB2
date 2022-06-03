package by.alina.SpringBootDB.mapper;

import by.alina.SpringBootDB.model.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {

        String columnLLabelId = "id";
        String columnLLabelName = "name";
        String columnLLabelAge = "age";

        return new Person(rs.getInt(columnLLabelId), rs.getString(columnLLabelName),rs.getInt(columnLLabelAge));
    }
}
