package by.alina.SpringBootDB.dtos;

import org.springframework.hateoas.RepresentationModel;

public class PersonDTO extends RepresentationModel<PersonDTO> {

    private Long id;
    private String name;
    private int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public PersonDTO(Long id, String name, int age) {
    }
}
