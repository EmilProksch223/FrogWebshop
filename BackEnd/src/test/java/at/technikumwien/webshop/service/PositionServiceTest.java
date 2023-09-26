package at.technikumwien.webshop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import at.technikumwien.webshop.model.Position;
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
    void shouldReturnPositionById() {
        // Mocking
        Position expectedPosition = new Position();
        when(positionRepository.findById(any())).thenReturn(Optional.of(expectedPosition));

        // Test
        Optional<Position> actualPosition = positionService.findById(1L);

        assertTrue(actualPosition.isPresent());
        assertEquals(expectedPosition, actualPosition.get());
        verify(positionRepository, times(1)).findById(1L);
    }
}