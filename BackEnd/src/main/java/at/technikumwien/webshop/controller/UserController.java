package at.technikumwien.webshop.controller;

import java.net.URI;

import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.technikumwien.webshop.dto.UserDTO;
import at.technikumwien.webshop.model.User;
import at.technikumwien.webshop.security.UserPrincipal;
import at.technikumwien.webshop.service.BadRequestException;
import at.technikumwien.webshop.service.TokenService;
import at.technikumwien.webshop.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserProfile(@PathVariable Long id, HttpServletRequest request) {
        String token = tokenService.extractTokenFromRequest(request);

        if (token != null) {
            Optional<UserPrincipal> userPrincipal = tokenService.parseToken(token);

            if (userPrincipal.isPresent()) {
                if (userPrincipal.get().getUserId().equals(id)) {
                    Optional<User> userProfile = userService.getUserById(id);
                    return ResponseEntity.ok(userProfile);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody @Valid UserDTO userDTO) {
        if (userService.existsByUsername(userDTO.getUsername())) {
            throw new BadRequestException("Benutzername existiert schon!");
        }
        User user = fromDTO(userDTO);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        User createdUser = userService.createUser(user);
        return ResponseEntity.created(URI.create("http://localhost:8080/users")).body(createdUser);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> updateUser(@RequestBody @Valid UserDTO userDTO) {
        Optional<User> optionalUser = userService.getUserById(userDTO.getId());
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setUsername(userDTO.getUsername());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setActive(userDTO.isActive());
            existingUser.setAdmin(userDTO.isAdmin());
            User updatedUser = userService.updateUser(existingUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> optionalUser = userService.getUserById(id);
        if (optionalUser.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private User fromDTO(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setActive(userDTO.isActive());
        user.setAdmin(userDTO.isAdmin());
        return user;
    }
}