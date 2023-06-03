package at.technikumwien.webshop.service;

import java.util.List;
import java.util.Optional;

import at.technikumwien.webshop.model.Product;
import at.technikumwien.webshop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ProductService {

    private ProductRepository productRepository;

    // /////////////////////////////////////////////////////////////////////////
    // Init
    // /////////////////////////////////////////////////////////////////////////

    public ProductService(ProductRepository repository) {
        this.productRepository = repository;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public List<Product> findByManaType(String manaSymbolString) {
        return productRepository.findByManaType(manaSymbolString);
    }
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product setActive(Long id) {
        var product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Product p = product.get();
        p.setActive(true);
        return save(p);
    }
}
