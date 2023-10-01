package at.technikumwien.webshop.repository;

import at.technikumwien.webshop.model.Cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUserId(Long userId);

    Optional<Cart> findCartByUserId(Long userId);

    Cart findCartIdByUserId(Long userId);
}
