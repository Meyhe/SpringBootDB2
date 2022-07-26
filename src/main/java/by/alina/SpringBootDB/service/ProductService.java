package by.alina.SpringBootDB.service;

import by.alina.SpringBootDB.exception.MyEntityNotFoundException;
import by.alina.SpringBootDB.model.Product;
import by.alina.SpringBootDB.repositorie.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product getProduct(Long id){
        return productRepository.findById(id).orElseThrow(()-> new MyEntityNotFoundException(id));
    }

    public List<Product> getListProducts(){
        return (List<Product>) productRepository.findAll();
    }

    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    public Product updateProduct(Product product, Long id){
        Product updateProduct = getProduct(id);

        updateProduct.setName(product.getName());
        updateProduct.setPrice(product.getPrice());

        return productRepository.save(updateProduct);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

}
