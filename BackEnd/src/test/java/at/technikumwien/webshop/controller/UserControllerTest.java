package at.technikumwien.webshop.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import at.technikumwien.webshop.model.User;
import at.technikumwien.webshop.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO: Test funktioniert nicht pls Help
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        // Here you can prepare for your test, e.g., insert test data into the database
    }

    @Test
    @WithMockUser(roles = "ADMIN") // Simulate a user with the ADMIN role
    public void testGetAllUserWithAdminRole() throws Exception {
        // Create dummy user data for the mock
        List<User> dummyUsers = new ArrayList<>();
        dummyUsers.add(new User());
        dummyUsers.add(new User());

        // Mock the userService method
        when(userService.getAllUsers()).thenReturn(dummyUsers);

        // Execute the HTTP GET request
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/users"))
                .andExpect(status().isOk())  // Here, the expected HTTP status code check is performed
                .andReturn();

        assertEquals("application/json", mvcResult.getResponse().getContentType());

        // Verify if the userService method was called
        verify(userService, times(1)).getAllUsers();
    }
}
