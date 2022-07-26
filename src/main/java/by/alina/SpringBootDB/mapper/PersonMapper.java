package by.alina.SpringBootDB.mapper;

import by.alina.SpringBootDB.dto.PersonDTO;
import by.alina.SpringBootDB.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") //<- без этого "Consider defining a bean of type 'by.alina.SpringBootDB.mapper.PersonMapper' in your configuration."
public interface PersonMapper {

    //много в каких примерах это есть, но I don't understand how and where I should use this
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonDTO personToPersonDTO (Person person);
}
