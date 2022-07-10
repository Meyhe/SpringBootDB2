package by.alina.SpringBootDB.assemblers;

import by.alina.SpringBootDB.controllers.PersonController;
import by.alina.SpringBootDB.controllers.ProductController;
import by.alina.SpringBootDB.dtos.ProductDTO;
import by.alina.SpringBootDB.models.Product;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductAssembler extends RepresentationModelAssemblerSupport<Product, ProductDTO> {

    public ProductAssembler(){
        super(ProductController.class, ProductDTO.class);
    }


    @Override
    public ProductDTO toModel(Product product) {
        ProductDTO productDTO = instantiateModel(product);

        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());

        productDTO.add(linkTo(methodOn(ProductController.class)
                .getProduct(product.getId()))
                .withRel("Product's details")
                .withType("GET"));

        productDTO.add(linkTo(methodOn(ProductController.class)
                .updateProduct(product, product.getId()))
                .withRel("Update product")
                .withType("PUT"));

        productDTO.add(linkTo(methodOn(ProductController.class)
                .deleteProduct(product.getId()))
                .withRel("Delete product")
                .withType("DELETE"));

        productDTO.add(linkTo(methodOn(ProductController.class)
                .getProducts())
                .withRel("Get all products")
                .withType("GET"));

        return productDTO;
    }

    @Override
    public CollectionModel<ProductDTO> toCollectionModel(Iterable<? extends Product> productList) {
        CollectionModel<ProductDTO> productDTOCollectionModel = super.toCollectionModel(productList);
        productDTOCollectionModel.add(linkTo(methodOn(PersonController.class)
                .getPeople())
                .withRel("Get people")
                .withType("GET"));
        return productDTOCollectionModel;
    }
}
