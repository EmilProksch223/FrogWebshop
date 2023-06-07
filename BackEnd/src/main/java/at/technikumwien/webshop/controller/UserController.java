package at.technikumwien.webshop.controller;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.technikumwien.webshop.dto.UserDTO;
import at.technikumwien.webshop.model.User;
import at.technikumwien.webshop.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUser(){
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid UserDTO userDTO) {
        User user = fromDTO(userDTO);
        User createdUser = userService.createUser(user);
        return ResponseEntity.created(URI.create("http://localhost:8080/users")).body(createdUser);
    }

    private User fromDTO(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setAdmin(userDTO.isAdmin());
        return user;
    }
}