package at.technikumwien.webshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import at.technikumwien.webshop.model.Product;
import at.technikumwien.webshop.repository.ProductRepository;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {
    
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StorageService storageService;

    @BeforeEach
    public void setUp() {
        productService = new ProductService(productRepository, storageService);
    }

    @Test
    public void shouldReturnAllFilteredProducts() {
        List<Product> dummyProducts = new ArrayList<>();
        dummyProducts.add(
                new Product("Dark Ritual", "Add 3 mana to your mana to your mana pool.", "1", 245.00, 10, "w", true));
        dummyProducts.add(new Product("Llanoward Elves", "Add 1 Mana to your mana pool.", "3", 0.30, 15, "w", true));
        
        when(productRepository.findAll()).thenReturn(dummyProducts);
        when(productRepository.findByActive(true)).thenReturn(dummyProducts);

        List<Product> result = productService.getAllFilteredProdcuts(null, null);
            
        assertEquals(dummyProducts, result);
        
    }
}