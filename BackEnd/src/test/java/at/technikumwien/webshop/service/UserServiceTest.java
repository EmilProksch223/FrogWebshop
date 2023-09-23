package at.technikumwien.webshop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
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
public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void shouldReturnAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAllUsers();
        assertEquals(userList, result);
    }

    @Test
    public void shouldReturnUserById() {
        User dummyUser = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(dummyUser));
        
        Optional<User> result = userService.getUserById(1L);
        
        assertTrue(result.isPresent());
        assertEquals(dummyUser, result.get());
    }
    

    @Test
    public void shouldSaveUserWithHashedPassword() {
        User userToCreate = new User();
        userToCreate.setUsername("testUser");
        userToCreate.setPassword("plainTextPassword");

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
            return user;
        });

        User createdUser = userService.createUser(userToCreate);

        verify(passwordEncoder).encode("plainTextPassword");

        verify(userRepository).save(any(User.class));

        assertEquals("testUser", createdUser.getUsername());
    }

    @Test
    public void shouldDeleteUser() {
        userService.deleteUser(1L);
        
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldCheckIfUsernameExists() {
        when(userRepository.existsByUsername("existingUsername")).thenReturn(true);
        when(userRepository.existsByUsername("nonExistingUsername")).thenReturn(false);
        
        assertTrue(userService.existsByUsername("existingUsername"));
        assertFalse(userService.existsByUsername("nonExistingUsername"));
    }
}
