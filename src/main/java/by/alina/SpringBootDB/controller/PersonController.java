package by.alina.SpringBootDB.controller;

import by.alina.SpringBootDB.assembler.PersonAssembler;
import by.alina.SpringBootDB.assembler.ProductAssembler;
import by.alina.SpringBootDB.converter.ProductConverter;
import by.alina.SpringBootDB.dto.PersonDTO;
import by.alina.SpringBootDB.dto.ProductDTO;
import by.alina.SpringBootDB.mapper.PersonMapper;
import by.alina.SpringBootDB.model.Product;

import by.alina.SpringBootDB.model.Person;
import by.alina.SpringBootDB.service.PersonService;
import by.alina.SpringBootDB.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;
    private final ProductService productService;

    private final ProductAssembler productAssembler;
    private final PersonAssembler personAssembler;

    private final ProductConverter productConverter;
    private final PersonMapper personMapper;

    @Autowired
    public PersonController(PersonService personService, ProductService productService,
                            PersonMapper personMapper, ProductConverter productConverter,
                            PersonAssembler personAssembler,ProductAssembler productAssembler){

        this.personService = personService;
        this.productService = productService;

        this.personMapper = personMapper;
        this.productConverter = productConverter;

        this.personAssembler = personAssembler;
        this.productAssembler = productAssembler;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<PersonDTO> getPeople(){
        List<Person> personList = personService.getPeople();
        return personAssembler.toCollectionModel(personList);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDTO createPerson(@RequestBody Person person){
        Person newPerson = personService.addPerson(person);
        return personMapper.personToPersonDTO(newPerson);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDTO updatePerson(@RequestBody Person person, @PathVariable Long id){
        Person updatePerson = personService.updatePerson(person, id);
        return personMapper.personToPersonDTO(updatePerson);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);

        //здесь что-то как-то ничего не накрутила
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/{id}/products")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> getPerson(@PathVariable Long id){
        List<Product> productList = personService.getPerson(id).getProductList();
        List<ProductDTO> productDTOList = new ArrayList<>();

        for (Product product : productList){
            ProductDTO productDTO = productAssembler.productDTOModel(product, id);
            productDTOList.add(productDTO);
        }

        return productDTOList;
    }

    @DeleteMapping("/{personId}/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProductFromPerson(@PathVariable Long personId, @PathVariable Long productId){
        Person person = personService.getPerson(personId);
        Product product = productService.getProduct(productId);

        person.removeProduct(product);
        personService.updatePerson(person, personId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{peopleId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO addProducts(@PathVariable Long peopleId, @RequestBody Product product){
        Person person = personService.getPerson(peopleId);

        person.addProduct(product);
        personService.updatePerson(person, peopleId);

        ProductDTO productDTO = productAssembler.productDTOModel(product, peopleId);

        return productDTO;
    }
}
