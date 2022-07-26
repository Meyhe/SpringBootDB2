package by.alina.SpringBootDB.converter;

import by.alina.SpringBootDB.dto.ProductDTO;
import by.alina.SpringBootDB.model.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter implements Converter<Product, ProductDTO> {

    public ProductDTO convert (Product source){

        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(source.getId());
        productDTO.setName(source.getName());
        productDTO.setPrice(source.getPrice());

        return productDTO;
    }
}
