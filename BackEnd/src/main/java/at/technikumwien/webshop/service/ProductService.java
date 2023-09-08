package at.technikumwien.webshop.service;

import java.util.List;
import java.util.Optional;

import at.technikumwien.webshop.model.Product;
import at.technikumwien.webshop.repository.ProductRepository;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

    private ProductRepository productRepository;
    private StorageService storageService;
    

    public ProductService(ProductRepository repository, StorageService storageService) {
        this.productRepository = repository;
        this.storageService = storageService;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public List<Product> findByManaType(String manaSymbolString) {
        return productRepository.findByManaType(manaSymbolString);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product setActive(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new EntityNotFoundException();
        }
        Product product = productOptional.get();
        product.setActive(true);
        return save(product);
    }

    public List<Product> getActiveProducts() {
        return productRepository.findByActive(true);
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    // Überflüssig?
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    public void deleteProductAndFile(Long productId) {
        // Suchen Sie das Produkt
        Optional<Product> optionalProduct = productRepository.findById(productId);
        
        if (optionalProduct.isPresent()) {
            // Produkt gefunden, entferne das zugehörige File
            Product product = optionalProduct.get();
            String imageUrl = product.getImageUrl();
            long imageId = Long.parseLong(imageUrl);
            storageService.deleteFile(imageId);
    
            // Lösche das Produkt
            productRepository.delete(product);
        } else {
            // Produkt nicht gefunden, du kannst hier eine Fehlerbehandlung hinzufügen oder einfach nichts tun
            // Zum Beispiel könntest du eine Fehlermeldung ausgeben oder eine Ausnahme werfen
        }
    }
}
