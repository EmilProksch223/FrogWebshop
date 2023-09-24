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
        Long positionId = 1L;
        Position expectedPosition = new Position();
        when(positionRepository.findById(positionId)).thenReturn(Optional.of(expectedPosition));

        // Test
        Optional<Position> actualPosition = positionService.findById(positionId);

        assertTrue(actualPosition.isPresent());
        assertEquals(expectedPosition, actualPosition.get());
        verify(positionRepository, times(1)).findById(positionId);
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
        Position positionToSave = new Position();

        when(tokenService.getUserIdFromToken(token)).thenReturn(userId);
        when(cartService.findByUserId(userId)).thenReturn(cart);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productService.getProductById(anyLong())).thenReturn(Optional.of(product));
        when(positionRepository.save(any(Position.class))).thenReturn(positionToSave);

        System.out.println("Cart in testSavePosition: " + cart);
        System.out.println("Product in testSavePosition: " + product);

        // Test
        Position savedPosition = positionService.save(new Position(), 1L, token);

        assertNotNull(savedPosition);
        assertEquals(positionToSave, savedPosition);
        assertEquals(cart, savedPosition.getCart());
        assertEquals(product, savedPosition.getProduct());

        verify(tokenService, times(1)).getUserIdFromToken(token);
        verify(cartService, times(1)).findByUserId(userId);
        verify(userRepository, times(1)).findById(userId);
        verify(productService, times(1)).getProductById(anyLong());
        verify(positionRepository, times(1)).save(any(Position.class));
    }
}
