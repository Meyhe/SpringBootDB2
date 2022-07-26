package by.alina.SpringBootDB.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDTO extends RepresentationModel<PersonDTO> {

    private Long id;
    private String name;
    private int age;
    private List<ProductDTO> productDTOList = new ArrayList<>();

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

    public List<ProductDTO> getProductList() {
        return productDTOList;
    }

    public void setProductList(List<ProductDTO> productDTOList) {
        this.productDTOList = productDTOList;
    }

    public PersonDTO() {
    }
}
