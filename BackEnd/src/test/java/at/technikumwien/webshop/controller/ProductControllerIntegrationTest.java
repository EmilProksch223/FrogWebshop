package at.technikumwien.webshop.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;

import at.technikumwien.webshop.config.SecurityConfig;
import at.technikumwien.webshop.dto.ProductDTO;
import at.technikumwien.webshop.model.Product;
import at.technikumwien.webshop.repository.ProductRepository;
import at.technikumwien.webshop.service.ProductService;
import at.technikumwien.webshop.service.StorageService;
import at.technikumwien.webshop.service.TokenService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ExtendWith(SpringExtension.class)
@Import({ ProductService.class, SecurityConfig.class })
public class ProductControllerIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ProductRepository productRepository;

        @MockBean
        private StorageService storageService;

        @MockBean
        private TokenService tokenService;

        @Test
        @WithMockUser(roles = "ADMIN")
        public void testGetAllFilteredProducts() throws Exception {
                List<Product> dummyProducts = new ArrayList<>();
                dummyProducts.add(
                                new Product("Dark Ritual", "Add 3 mana to your mana to your mana pool.", "1", 245.00,
                                                10, "w", 3L, true));
                dummyProducts.add(new Product("Llanoward Elves", "Add 1 Mana to your mana pool.", "3", 0.30, 15, "w", 1L,
                                true));
                Gson gson = new Gson();

                when(productRepository.findAll()).thenReturn(dummyProducts);

                this.mockMvc.perform(MockMvcRequestBuilders
                                .get("/products?activeFilter=true&searchterm=D"))
                                .andExpect(status().isOk())
                                .andExpect(result -> gson.toJson(dummyProducts));
        }

        @Test
        @WithMockUser
        public void testAccessRestrictedEndpointWithNoRoles() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders
                                .get("/products?activeFilter=true&searchterm=D")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isForbidden());
        }

        @Test
        public void testGetAllActiveFilteredProducts() throws Exception {
                List<Product> dummyProducts = new ArrayList<>();
                dummyProducts.add(
                                new Product("Dark Ritual", "Add 3 mana to your mana to your mana pool.", "1", 245.00,
                                10, "w", 3L, true));
                dummyProducts.add(new Product("Llanoward Elves", "Add 1 Mana to your mana pool.", "3", 0.30, 15, "w",
                                3L, true));
                Gson gson = new Gson();

                when(productRepository.findByActive(true)).thenReturn(dummyProducts);

                this.mockMvc.perform(MockMvcRequestBuilders
                                .get("/products/active?manasymbols=w&searchterm=D"))
                                .andExpect(status().isOk())
                                .andExpect(result -> gson.toJson(dummyProducts));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        public void testCreateProductAsAdmin() throws Exception {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setActive(false);
                productDTO.setName("testName");
                productDTO.setDescription("testDescription");
                productDTO.setImageUrl("1");
                productDTO.setManaType("w");
                productDTO.setManaCost(1L);
                productDTO.setPrice(1);
                productDTO.setQuantity(1);

                Product product = new Product();
                Gson gson = new Gson();

                when(productRepository.save(any())).thenReturn(product);

                this.mockMvc.perform(MockMvcRequestBuilders
                                .post("/products")
                                .content(gson.toJson(productDTO))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isCreated())
                                .andExpect(result -> gson.toJson(product));
        }
}
