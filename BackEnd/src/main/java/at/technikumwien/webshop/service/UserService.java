package at.technikumwien.webshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import at.technikumwien.webshop.model.User;
import at.technikumwien.webshop.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
        return userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
