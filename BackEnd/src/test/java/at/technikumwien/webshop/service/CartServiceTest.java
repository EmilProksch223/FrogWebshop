package at.technikumwien.webshop.service;

import at.technikumwien.webshop.model.Cart;
import at.technikumwien.webshop.model.Position;
import at.technikumwien.webshop.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CartServiceTest {

    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        cartService = new CartService(cartRepository, tokenService);
    }

    @Test
    void testSaveCart() {
        Cart cart = new Cart();
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart savedCart = cartService.save(cart);

        assertNotNull(savedCart);
        assertEquals(cart, savedCart);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testFindByUserId() {
        Long userId = 1L;
        Cart cart = new Cart();
        when(cartRepository.findByUserId(userId)).thenReturn(cart);

        Cart foundCart = cartService.findByUserId(userId);

        assertNotNull(foundCart);
        assertEquals(cart, foundCart);
        verify(cartRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testGetPositionsInCart() {
        Long userId = 1L;
        String token = "validToken";

        when(tokenService.getUserIdFromToken(token)).thenReturn(userId);

        Cart cart = new Cart();
        Set<Position> positions = new HashSet<>();
        Position position1 = new Position();
        Position position2 = new Position();
        positions.add(position1);
        positions.add(position2);
        cart.setPositions(positions);

        when(cartRepository.findCartByUserId(userId)).thenReturn(Optional.of(cart));

        Set<Position> result = cartService.getPositionsInCart(token);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(position1));
        assertTrue(result.contains(position2));
    }

    @Test
    void testGetPositionsInCartWithInvalidToken() {
        String invalidToken = "invalidToken";

        when(tokenService.getUserIdFromToken(invalidToken)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> cartService.getPositionsInCart(invalidToken));
    }
}
