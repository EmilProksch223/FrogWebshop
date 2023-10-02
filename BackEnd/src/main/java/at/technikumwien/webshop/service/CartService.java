package at.technikumwien.webshop.service;

import at.technikumwien.webshop.model.Cart;
import at.technikumwien.webshop.model.Position;
import at.technikumwien.webshop.repository.CartRepository;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class CartService {
    
    private CartRepository cartRepository;
    private final TokenService tokenService;

    public CartService(CartRepository cartRepository, TokenService tokenService) {
        this.cartRepository = cartRepository;
        this.tokenService = tokenService;
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
    
    public Set<Position> getPositionsInCart(String token) {

        Long userId = tokenService.getUserIdFromToken(token);
        Optional<Cart> cartOptional = cartRepository.findCartByUserId(userId);

        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            return cart.getPositions();
        } else {
            throw new EntityNotFoundException();
        }
    }

    public void deleteCart(long id) {
        cartRepository.deleteById(id);
    }


}
