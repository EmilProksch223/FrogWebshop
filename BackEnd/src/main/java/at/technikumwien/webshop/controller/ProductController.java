package at.technikumwien.webshop.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import at.technikumwien.webshop.dto.ProductDTO;
import at.technikumwien.webshop.model.Product;
import at.technikumwien.webshop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService service;

    // /////////////////////////////////////////////////////////////////////////
    // Init
    // /////////////////////////////////////////////////////////////////////////

    public ProductController(ProductService service) {
        this.service = service;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////


    @GetMapping
    public List<Product> getAllProducts(
            @RequestParam(name = "manasymbols", required = false) String manaSymbolsString, 
            @RequestParam(name = "searchterm", required = false) String searchterm) {
        
        List<Product> allProducts = service.getAllProducts();
        List<Product> matchingProducts = new ArrayList<>();
    
        if (manaSymbolsString == null && searchterm == null) {
            return allProducts;
        }
        for (Product product : allProducts) {
            if (manaSymbolsString != null && !product.getManaType().toLowerCase().contains(manaSymbolsString.toLowerCase())) {
                continue; // Skip this product
            }
            if (searchterm != null && !product.getName().toLowerCase().contains(searchterm.toLowerCase())) {
                continue; // Skip this product
            }
            matchingProducts.add(product);
        }
        return matchingProducts;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductDTO productDTO) {
        Product product = service.save(fromDTO(productDTO));
        
        return ResponseEntity.created(URI.create("http://localhost:8080/products")).body(product);
    }

    @PutMapping("/setActive/{id}")
    public Product setActive(@PathVariable Long id) {
        return service.setActive(id);
    }

    // /////////////////////////////////////////////////////////////////////////
    // Util
    // /////////////////////////////////////////////////////////////////////////

    private static Product fromDTO(ProductDTO productDTO) {
        return new Product(productDTO.getName(),
                           productDTO.getDescription(),
                           productDTO.getImageUrl(),
                           productDTO.getPrice(),
                           productDTO.getQuantity(),
                           productDTO.getManaType());
    }
}
