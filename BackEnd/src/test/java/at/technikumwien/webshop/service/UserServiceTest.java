package at.technikumwien.webshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import at.technikumwien.webshop.config.SecurityConfig;
import at.technikumwien.webshop.model.User;
import at.technikumwien.webshop.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserService.class)
@Import(SecurityConfig.class)
public class UserServiceTest {
    
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @MockBean
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void whenGetAllUsers_thenGetAListOfUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());

        when(userRepository.findAll()).thenReturn(userList);

        List<User> newUserList = userService.getAllUsers();

        assertEquals(userList, newUserList);
    }
}
