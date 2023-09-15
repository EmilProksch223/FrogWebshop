package at.technikumwien.webshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import at.technikumwien.webshop.model.Product;
import at.technikumwien.webshop.repository.ProductRepository;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

    private ProductRepository productRepository;
    private StorageService storageService;
    

    public ProductService(ProductRepository productRepository, StorageService storageService) {
        this.productRepository = productRepository;
        this.storageService = storageService;
    }

    public List<Product> getAllFilteredProdcuts(String searchterm, Boolean activeFilter) {
        List<Product> filteredProducts = new ArrayList<>();
        List<Product> allProducts = productRepository.findAll();
        if (searchterm == null && activeFilter == null) {
            return allProducts;
        }
        for (Product product : allProducts) {
            if (searchterm != null && (!product.getName().toLowerCase().contains(searchterm.toLowerCase()))) {
                continue;
            }
            if (activeFilter != null && product.isActive() != activeFilter) {
                continue;
            }
            filteredProducts.add(product);
        }
        return filteredProducts;
    }

    public List<Product> getActiveFilteredProducts(String searchterm, String manaSymbolsString) {
        List<Product> activeProducts = new ArrayList<>();
        List<Product> allProducts = productRepository.findByActive(true);

        if (manaSymbolsString == null && searchterm == null) {
            return allProducts;
        }
        for (Product product : allProducts) {
            if (manaSymbolsString != null
                    && !product.getManaType().toLowerCase().contains(manaSymbolsString.toLowerCase())) {
                continue;
            }
            if (searchterm != null && !product.getName().toLowerCase().contains(searchterm.toLowerCase())) {
                continue;
            }
            activeProducts.add(product);
        }
        return activeProducts;
    }

    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }
/* 
    public List<Product> findByManaType(String manaSymbolString) {
        return productRepository.findByManaType(manaSymbolString);
    }
*/
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }
/* 
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
*/

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }
/* 
    // Überflüssig?
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }
*/
    public void deleteProductAndFile(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            String imageUrl = product.getImageUrl();
            long imageId = Long.parseLong(imageUrl);
            storageService.deleteFile(imageId);
    
            productRepository.delete(product);
        } else {
            // Produkt nicht gefunden, du kannst hier eine Fehlerbehandlung hinzufügen oder einfach nichts tun
            // Zum Beispiel könntest du eine Fehlermeldung ausgeben oder eine Ausnahme werfen
        }
    }
}
