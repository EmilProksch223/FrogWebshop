package at.technikumwien.webshop.controller;

import com.google.gson.Gson;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import at.technikumwien.webshop.config.SecurityConfig;
import at.technikumwien.webshop.dto.LoginDTO;
import at.technikumwien.webshop.model.User;
import at.technikumwien.webshop.repository.UserRepository;
import at.technikumwien.webshop.service.AuthenticationService;
import at.technikumwien.webshop.service.TokenService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

@WebMvcTest(AuthenticationContoller.class)
@ExtendWith(SpringExtension.class)
@Import({ AuthenticationService.class, SecurityConfig.class })
public class AuthenticationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TokenService tokenService;

    @Test
    public void testLoginWithValidCredentials() throws Exception {
        LoginDTO loginDTO = new LoginDTO("testUsername", "testPassword");
        User user = new User();
        user.setUsername("testUsername");
        user.setPassword("$2a$10$0OYiJE2PAUOdpCrV5oSh3ODautltotRqlLOv8ob5tworyr5rLIB16");
        user.setActive(true);
        Gson gson = new Gson();

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/login")
                .content(gson.toJson(loginDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginWithInvalidCredentials() throws Exception {
        LoginDTO loginDTO = new LoginDTO("testUsername", "testPassword");
        User user = new User();
        user.setUsername("testUsername");
        user.setPassword("$2a$10$0OYiJE2PAUOdpCrV5oSh3ODautltotRqlLOv8ob5tworyr5rLIB16");
        user.setActive(false);
        Gson gson = new Gson();

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/login")
                .content(gson.toJson(loginDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
