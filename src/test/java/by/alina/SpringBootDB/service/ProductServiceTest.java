package by.alina.SpringBootDB.service;

import by.alina.SpringBootDB.model.Product;
import by.alina.SpringBootDB.repositorie.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private Product product;
    private List<Product> productList;

    @BeforeEach
    public void setUp(){
        product = new Product(1L, "Xleb", 19);
    }

    @AfterEach
    public void tearDown(){
        product = null;
        productList = null;
    }

    @Test
    public void whenGetAllProducts_thenReturnAllProducts(){
        //given
        productList = Arrays.asList(product);
        when(productRepository.findAll()).thenReturn(productList);

        //when
        List<Product> actualProductList = productService.getListProducts();

        //then
        assertEquals(productList, actualProductList);
        verify(productRepository,times(1)).findAll();
    }

    @Test
    public void whenGetProductById_thenReturnProductOfThatId(){
        //given
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        //when
        Product actualProduct = productService.getProduct(product.getId());

        //then
        assertEquals(product, actualProduct);
        verify(productRepository, times(1)).findById(product.getId());
    }

    @Test
    public void whenSaveProduct_thenReturnProduct(){
        //given
        when(productRepository.save(product)).thenReturn(product);

        //when
        Product actualProduct = productService.addProduct(product);

        //then
        assertEquals(product, actualProduct);
        verify(productRepository,times(1)).save(product);
    }

    //в этом тесте я не уверена
    @Test
    public void whenUpdateProduct_thenReturnUpdateProduct(){
        //given
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        //when
        Product getProduct = productService.getProduct(id);
        getProduct.setName("Moloko");
        getProduct.setPrice(3);

        Product upProduct = productService.updateProduct(getProduct, id);

        //then
        assertEquals("Moloko", upProduct.getName());
        assertEquals(3, upProduct.getPrice());
        assertEquals(1, upProduct.getId());
    }

    @Test
    public void whenDeleteProductById_thenDeleteProduct(){
        //given
        doNothing().when(productRepository).deleteById(product.getId());

        //when
        productService.deleteProduct(product.getId());

        //then
        verify(productRepository,times(1)).deleteById(product.getId());
    }
}
