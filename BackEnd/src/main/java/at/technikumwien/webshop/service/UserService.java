package at.technikumwien.webshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import at.technikumwien.webshop.model.Cart;
import at.technikumwien.webshop.model.User;
import at.technikumwien.webshop.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CartService cartService;
    private final PositionService positionService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, CartService cartService, PositionService positionService,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.positionService = positionService;
        this.passwordEncoder = passwordEncoder;

    }

    /////
    // Methods
    /////

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public User createUser(@RequestBody User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(long id) {
        Cart userCart = cartService.findByUserId(id);

        if(userCart != null) {
            if(positionService.findByCartId(userCart.getId()) != null) {
                positionService.deletePositionWithCardId(userCart.getId());
            }
            cartService.deleteCart(userCart.getId());
        }
        userRepository.deleteById(id);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
