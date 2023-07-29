package at.technikumwien.webshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import at.technikumwien.webshop.dto.ProductDTO;
import at.technikumwien.webshop.model.Product;
import at.technikumwien.webshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository repository) {
        this.productRepository = repository;
    }

    /////
    //Methods
    /////

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getAllProductsFiltered(String searchterm) {
        if (searchterm == null || searchterm.isBlank()) {
            return getAllProducts();
        }
        List<Product> allProducts = getAllProducts();
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(searchterm.toLowerCase())) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    public List<Product> getActiveProducts() {
        return productRepository.findByActive(true);
    }

    public List<Product> getActiveProductsFiltered(String manaSymbolsString, String searchterm) {
        List<Product> activeProducts = getActiveProducts();
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : activeProducts) {
            if (manaSymbolsString != null
                    && !product.getManaType().toLowerCase().contains(manaSymbolsString.toLowerCase())) {
                continue;
            }
            if (searchterm != null && !searchterm.isBlank()
                    && !product.getName().toLowerCase().contains(searchterm.toLowerCase())) {
                continue;
            }
            filteredProducts.add(product);
        }
        return filteredProducts;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProductFromDTO(Product existingProduct, ProductDTO productDTO) {
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setImageUrl(productDTO.getImageUrl());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setQuantity(productDTO.getQuantity());
        existingProduct.setManaType(productDTO.getManaType());
        existingProduct.setActive(productDTO.isActive());
        return updateProduct(existingProduct);
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
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

    public List<Product> findByManaType(String manaSymbolString) {
        return productRepository.findByManaType(manaSymbolString);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }
}
