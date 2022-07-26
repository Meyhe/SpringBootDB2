package by.alina.SpringBootDB.assembler;

import by.alina.SpringBootDB.controller.PersonController;
import by.alina.SpringBootDB.controller.ProductController;
import by.alina.SpringBootDB.converter.ProductConverter;
import by.alina.SpringBootDB.dto.ProductDTO;
import by.alina.SpringBootDB.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductAssembler extends RepresentationModelAssemblerSupport<Product, ProductDTO> {

    private final ProductConverter productConverter;

    @Autowired
    public ProductAssembler(ProductConverter productConverter){
        super(ProductController.class, ProductDTO.class);
        this.productConverter = productConverter;
    }

    @Override
    public ProductDTO toModel(Product product) {
        ProductDTO productDTO = productConverter.convert(product);

        productDTO.add(linkTo(methodOn(ProductController.class)
                .getProduct(product.getId()))
                .withRel("Details of PRODUCT")
                .withType("GET"));

        productDTO.add(linkTo(methodOn(ProductController.class)
                .updateProduct(product, product.getId()))
                .withRel("Update PRODUCT")
                .withType("PUT"));

        productDTO.add(linkTo(methodOn(ProductController.class)
                .deleteProduct(product.getId()))
                .withRel("Delete PRODUCT")
                .withType("DELETE"));

        return productDTO;
    }

    public ProductDTO productDTOModel (Product product, Long id){
        ProductDTO productDTO = productConverter.convert(product);

        productDTO.add(linkTo(methodOn(ProductController.class)
                .updateProduct(product, id))
                .withRel("Update PRODUCT")
                .withType("PUT"));

        productDTO.add(linkTo((methodOn(PersonController.class)
                .addProducts(id, product)))
                .withRel("Add PRODUCT")
                .withType("POST"));

        productDTO.add(linkTo(methodOn(PersonController.class)
                    .deleteProductFromPerson(id, product.getId()))
                    .withRel("Delete PRODUCT ")
                    .withType("DELETE"));

        productDTO.add(linkTo(methodOn(PersonController.class)
                .getPeople())
                .withRel("Get PEOPLE")
                .withType("GET"));

        return productDTO;
    }

    @Override
    public CollectionModel<ProductDTO> toCollectionModel(Iterable<? extends Product> productList) {
        CollectionModel<ProductDTO> productDTOCollectionModel = super.toCollectionModel(productList);
        return productDTOCollectionModel;
    }
}
