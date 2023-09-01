package at.technikumwien.webshop.service;

import at.technikumwien.webshop.model.Cart;
import at.technikumwien.webshop.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    
    private CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    /////
    //Methods
    /////

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart findByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
