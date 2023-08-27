package at.technikumwien.webshop.controller;

import at.technikumwien.webshop.dto.LoginDTO;
import at.technikumwien.webshop.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationContoller {

    private final AuthenticationService authenticationService;

    /////
    //Init
    /////
    public AuthenticationContoller(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /////
    //Methods
    /////

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        String token = authenticationService.login(loginDTO.getUsername(), loginDTO.getPassword());
        return ResponseEntity.ok("Bearer " + token);
    }
}

