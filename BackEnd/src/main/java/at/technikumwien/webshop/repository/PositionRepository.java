package at.technikumwien.webshop.repository;

import at.technikumwien.webshop.model.Cart;
import at.technikumwien.webshop.model.Position;
import at.technikumwien.webshop.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    void deleteByIdAndCartId(Long id, Long cartId);

    Position findByCartAndProduct(Cart cart, Product product);


    
}
