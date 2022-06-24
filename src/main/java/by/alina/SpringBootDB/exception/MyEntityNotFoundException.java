package by.alina.SpringBootDB.exception;

import javax.persistence.EntityNotFoundException;

public class MyEntityNotFoundException extends EntityNotFoundException {

    public MyEntityNotFoundException(Long id){
        super("Entity is not found, id =  " + id);
    }
}
