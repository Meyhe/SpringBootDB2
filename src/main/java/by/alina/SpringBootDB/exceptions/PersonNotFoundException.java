package by.alina.SpringBootDB.exceptions;

import by.alina.SpringBootDB.model.Person;

public class PersonNotFoundException extends RuntimeException{
    private String message;

    public PersonNotFoundException (String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
