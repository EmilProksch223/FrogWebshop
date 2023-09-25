package at.technikumwien.webshop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import at.technikumwien.webshop.model.Cart;
import at.technikumwien.webshop.model.Position;
import at.technikumwien.webshop.model.Product;
import at.technikumwien.webshop.model.User;
import at.technikumwien.webshop.repository.PositionRepository;
import at.technikumwien.webshop.repository.UserRepository;

@ExtendWith(SpringExtension.class)
class PositionServiceTest {

    private PositionService positionService;

    @Mock
    private PositionRepository positionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartService cartService;

    @Mock
    private ProductService productService;

    @Mock
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        positionService = new PositionService(positionRepository, userRepository, cartService, productService,
                tokenService);
    }

    @Test
    void testFindById() {
        // Mocking
        Position expectedPosition = new Position();
        when(positionRepository.findById(any())).thenReturn(Optional.of(expectedPosition));

        // Test
        Optional<Position> actualPosition = positionService.findById(1L);

        assertTrue(actualPosition.isPresent());
        assertEquals(expectedPosition, actualPosition.get());
        verify(positionRepository, times(1)).findById(1L);
    }

    // TODO: savePostionTest
    @Test
    void testSavePosition() {
        // Mocking
        Long userId = 1L;
        String token = "validToken";
        Cart cart = new Cart();
        User user = new User();
        Product product = new Product();
        Position positionToSave = new Position(userId, 1);
        positionToSave.setCart(cart);
        positionToSave.setProduct(product);
    
        when(tokenService.getUserIdFromToken(token)).thenReturn(userId);
        when(cartService.findByUserId(any())).thenReturn(cart);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productService.getProductById(anyLong())).thenReturn(Optional.of(product));
        when(positionRepository.save(any(Position.class))).thenReturn(positionToSave);
    
        // Test
        Position savedPosition = positionService.save(positionToSave, 1L, token);
    
        // Additional checks
        assertNotNull(positionToSave, "Position to save is null");
        assertNotNull(savedPosition, "Saved position is null");
        assertEquals(positionToSave, savedPosition, "Saved position is not the same as the position to save");
        assertNotNull(savedPosition.getCart(), "Saved position cart is null");
        assertNotNull(savedPosition.getProduct(), "Saved position product is null");
    
        // Verify that userRepository.findById(1L) was called
        verify(userRepository, times(1)).findById(any());
    
        verify(tokenService, times(1)).getUserIdFromToken(token);
        verify(cartService, times(1)).findByUserId(any());
        verify(userRepository, times(1)).findById(userId);
        verify(productService, times(1)).getProductById(anyLong());
        verify(positionRepository, times(1)).save(any(Position.class));
    }
}
