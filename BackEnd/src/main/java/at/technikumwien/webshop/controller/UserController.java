package at.technikumwien.webshop.controller;

import java.net.URI;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.technikumwien.webshop.dto.AddressDTO;
import at.technikumwien.webshop.dto.UserDTO;
import at.technikumwien.webshop.model.Address;
import at.technikumwien.webshop.model.User;
import at.technikumwien.webshop.service.BadRequestException;
import at.technikumwien.webshop.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    /////
    //Init
    /////

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /////
    //Methods
    /////

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}/address")
    public ResponseEntity<UserDTO> getUserWithAddress(@PathVariable Long id) {
        Optional<User> optionalUser = userService.getUserById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserDTO userDTO = fromUserAndAddress(user);
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody @Valid UserDTO userDTO) {
        if (userService.existsByUsername(userDTO.getUsername())) {
            throw new BadRequestException("Benutzername existiert schon!");
        }
        User user = fromDTO(userDTO);

        User createdUser = userService.createUser(user);
        return ResponseEntity.created(URI.create("http://localhost:8080/users")).body(createdUser);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> updateUser(@RequestBody @Valid UserDTO userDTO) {
        Optional<User> optionalUser = userService.getUserById(userDTO.getId());
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            fromDTO(existingUser, userDTO);

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

    /////
    //UserDTO-Objekt in User-Objekt
    /////

    private User fromDTO(User existingUser, UserDTO userDTO) {
        existingUser.setUsername(userDTO.getUsername());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.setActive(userDTO.isActive());
        existingUser.setAdmin(userDTO.isAdmin());
        return existingUser;
    }

    private User fromDTO(UserDTO userDTO) {
        return fromDTO(new User(), userDTO);
    }

    private UserDTO fromUserAndAddress(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setActive(user.isActive());
        userDTO.setAdmin(user.isAdmin());

        Address address = user.getAddress();
        if (address != null) {
            AddressDTO addressDTO = new AddressDTO();
            address.setFirstName(addressDTO.getFirstName());
            address.setLastName(addressDTO.getLastName());
            address.setGender(addressDTO.getGender());
            address.setStreet(addressDTO.getStreet());
            address.setAddressLine2(addressDTO.getAddressLine2());
            address.setPostalCode(addressDTO.getPostalCode());
            address.setCity(addressDTO.getCity());
            address.setCountry(addressDTO.getCountry());
            userDTO.setAddress(addressDTO);
        }
        return userDTO;
    }
}