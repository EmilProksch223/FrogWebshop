package at.technikumwien.webshop.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import at.technikumwien.webshop.model.User;
import at.technikumwien.webshop.security.UserPrincipal;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TokenService.class)
public class TokenServiceTest {

    private TokenService tokenService;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        tokenService = new TokenService();
    }

    @Test
    public void shouldGenerateToken() {
        User user = new User();
        user.setUsername("testUser");
        user.setAdmin(false);

        String token = tokenService.generateToken(user);

        assertNotNull(token);
    }

    @Test
    public void shouldParseValidToken() {
        User user = new User();
        user.setUsername("testUser");
        user.setAdmin(false);

        String validToken = tokenService.generateToken(user);

        UserPrincipal actualUserPrincipal = tokenService.parseToken(validToken).orElse(null);

        assertNotNull(actualUserPrincipal);
        assertEquals(user.getUsername(), actualUserPrincipal.getUsername());
        assertEquals(user.isAdmin(), actualUserPrincipal.isAdmin());
    }
}
