package by.alina.SpringBootDB.controllers;

import by.alina.SpringBootDB.assemblers.ProductAssembler;
import by.alina.SpringBootDB.dtos.ProductDTO;
import by.alina.SpringBootDB.models.Product;
import by.alina.SpringBootDB.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")

public class ProductController {

    private final ProductService productService;
    private final ProductAssembler productAssembler;

    @Autowired
    public ProductController(ProductService productService, ProductAssembler productAssembler){
        this.productService = productService;
        this.productAssembler = productAssembler;
    }


    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public CollectionModel<ProductDTO> getProducts(){
        List<Product> productList = productService.getListProducts();
        return productAssembler.toCollectionModel(productList);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public RepresentationModel<ProductDTO> getProduct(@PathVariable Long id){
        Product product = productService.getProduct(id);
        return productAssembler.toModel(product);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public RepresentationModel<ProductDTO> createProduct (@RequestBody Product product){
        Product newProduct = productService.addProduct(product);
        return productAssembler.toModel(newProduct);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public RepresentationModel<ProductDTO> updateProduct(@RequestBody Product product, @PathVariable Long id){
        Product updateProduct = productService.updateProduct(product, id);
        return productAssembler.toModel(updateProduct);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
