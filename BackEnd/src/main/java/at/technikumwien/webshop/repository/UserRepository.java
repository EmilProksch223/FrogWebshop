package at.technikumwien.webshop.repository;

import java.util.Optional;

import at.technikumwien.webshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);
}
