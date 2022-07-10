package by.alina.SpringBootDB.controllers;

import by.alina.SpringBootDB.assemblers.PersonAssembler;
import by.alina.SpringBootDB.dtos.PersonDTO;
import by.alina.SpringBootDB.dtos.ProductDTO;
import by.alina.SpringBootDB.models.Product;
import org.springframework.hateoas.CollectionModel;

import by.alina.SpringBootDB.models.Person;
import by.alina.SpringBootDB.services.PersonService;
import by.alina.SpringBootDB.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;
    private final ProductService productService;
    private final PersonAssembler personAssembler;

    @Autowired
    public PersonController(PersonService personService, ProductService productService, PersonAssembler personAssembler){
        this.personService = personService;
        this.productService = productService;
        this.personAssembler = personAssembler;
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public CollectionModel<PersonDTO> getPeople() {
        List<Person> personList = personService.getPeople();
        return personAssembler.toCollectionModel(personList);
    }

    @GetMapping(value = "/{id}/products")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ProductDTO> getPerson(@PathVariable Long id){
        List<Product> productList = personService.getPerson(id).getProductList();
        List<ProductDTO> productDTOList = new ArrayList<>();

        for (Product product : productList){
            ProductDTO productDTO = new ProductDTO(product.getId(), product.getName(), product.getPrice());
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());

            productDTO.add(linkTo(methodOn(PersonController.class)
                    .deleteProductFromPerson(id, product.getId()))
                    .withRel("Delete product")
                    .withType("DELETE"));

            productDTO.add(linkTo(methodOn(ProductController.class)
                    .updateProduct(product, id))
                    .withRel("Update product")
                    .withType("PUT"));

            productDTO.add(linkTo(methodOn(PersonController.class)
                    .getPeople())
                    .withRel("Get people")
                    .withType("GET"));

            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public RepresentationModel<PersonDTO> createPerson(@RequestBody Person person){
        Person newPerson = personService.addPerson(person);
        return personAssembler.toModel(newPerson);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public RepresentationModel<PersonDTO> updatePerson(@RequestBody Person person, @PathVariable Long id){
        Person updatePerson = personService.updatePerson(person, id);
        return personAssembler.toModel(updatePerson);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{personId}/products/{productId}")
    public ResponseEntity<Void> deleteProductFromPerson(@PathVariable Long personId, @PathVariable Long productId){
        Person person = personService.getPerson(personId);
        Product product = productService.getProduct(productId);

        person.removeProduct(product);
        personService.updatePerson(person, personId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/{peopleId}/products")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ProductDTO addProducts(@PathVariable Long peopleId, @RequestBody Product product){
        Person person = personService.getPerson(peopleId);
        Product product1 = product;

        person.addProduct(product1);
        personService.updatePerson(person, peopleId);

        ProductDTO productDTO = new ProductDTO(product.getId(), product.getName(), product.getPrice());
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());

        productDTO.add(linkTo(methodOn(PersonController.class)
                .deleteProductFromPerson(peopleId, product.getId()))
                .withRel("Delete product")
                .withType("DELETE"));

        productDTO.add(linkTo(methodOn(ProductController.class)
                .updateProduct(product, peopleId))
                .withRel("Update product")
                .withType("PUT"));

        productDTO.add(linkTo(methodOn(PersonController.class)
                .getPeople())
                .withRel("Get people")
                .withType("GET"));

        return productDTO;
    }
}
