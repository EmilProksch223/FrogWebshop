package at.technikumwien.webshop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import at.technikumwien.webshop.model.User;
import at.technikumwien.webshop.repository.UserRepository;

@ExtendWith(SpringExtension.class)
public class AuthenticationServiceTest {

    private AuthenticationService authenticationService;

    @Mock
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        authenticationService = new AuthenticationService(tokenService, userRepository, passwordEncoder);
    }

    @Test
    public void testLoginWithValidCredentials() {
        String username = "testUser";
        String password = "testPassword";
        String hashedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setActive(true);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

        String token = "testToken";
        when(tokenService.generateToken(user)).thenReturn(token);

        String result = authenticationService.login(username, password);

        assertEquals(token, result);
    }

    @Test
    public void testLoginWithInvalidUsername() {
        String username = "nonExistentUser";
        String password = "testPassword";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> authenticationService.login(username, password));
    }

    @Test
    public void testLoginWithInactiveUser() {
        String username = "inactiveUser";
        String password = "testPassword";

        User inactiveUser = new User();
        inactiveUser.setUsername(username);
        inactiveUser.setActive(false);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(inactiveUser));

        assertThrows(BadRequestException.class, () -> authenticationService.login(username, password));
    }

    @Test
    public void testLoginWithInvalidPassword() {
        String username = "testUser";
        String password = "testPassword";
        String hashedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setActive(true);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        assertThrows(BadRequestException.class, () -> authenticationService.login(username, password));
    }
}
