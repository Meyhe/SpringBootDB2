package by.alina.SpringBootDB.controller;

import by.alina.SpringBootDB.assembler.ProductAssembler;
import by.alina.SpringBootDB.converter.ProductConverter;
import by.alina.SpringBootDB.dto.ProductDTO;
import by.alina.SpringBootDB.model.Product;
import by.alina.SpringBootDB.service.ProductService;
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
    private final ProductConverter productConverter;

    @Autowired
    public ProductController(ProductService productService, ProductAssembler productAssembler, ProductConverter productConverter){
        this.productService = productService;
        this.productAssembler = productAssembler;
        this.productConverter = productConverter;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<ProductDTO> getProducts(){
        List<Product> productList = productService.getListProducts();
        return productAssembler.toCollectionModel(productList);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RepresentationModel<ProductDTO> getProduct(@PathVariable Long id){
        Product product = productService.getProduct(id);
        ProductDTO productDTO = productConverter.convert(product);
        return productDTO;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RepresentationModel<ProductDTO> createProduct (@RequestBody Product product){
        Product newProduct = productService.addProduct(product);
        return productAssembler.toModel(newProduct);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RepresentationModel<ProductDTO> updateProduct(@RequestBody Product product, @PathVariable Long id){
        Product updateProduct = productService.updateProduct(product, id);
        return productAssembler.toModel(updateProduct);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
