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

    public List<Product> getActiveFilteredProducts(String searchterm, String manaSymbolsString, Long manaCost) {
        List<Product> activeProducts = new ArrayList<>();
        List<Product> allActiveProducts = productRepository.findByActive(true);
        
        if (manaSymbolsString == null && searchterm == null) {
            return allActiveProducts;
        }
        
        for (Product product : allActiveProducts) {
            if (searchterm != null && !product.getName().toLowerCase().contains(searchterm.toLowerCase())) {
                continue;
            }
        
            if (manaSymbolsString != null) {
                String manaType = product.getManaType().toLowerCase();
                boolean allSymbolsContained = true;
        
                for (char symbol : manaSymbolsString.toCharArray()) {
                    if (manaType.indexOf(Character.toLowerCase(symbol)) == -1) {
                        allSymbolsContained = false;
                        break;
                    }
                }
        
                if (!allSymbolsContained) {
                    continue;
                }
            }
        
            activeProducts.add(product);
        }
        
        return activeProducts;
    }

    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductAndFile(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            String imageUrl = product.getImageUrl();
            long imageId = Long.parseLong(imageUrl);
            storageService.deleteFile(imageId);
    
            productRepository.delete(product);
        } else {
        }
    }
}
