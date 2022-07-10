package by.alina.SpringBootDB.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundByNameException extends EntityNotFoundException {
    public EntityNotFoundByNameException(String name){
        super("Entity is not found, name =  " + name);
    }
}
