package at.technikumwien.webshop.controller;

import at.technikumwien.webshop.model.Cart;
import at.technikumwien.webshop.service.CartService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/carts")
public class CartController {
    
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @ResponseStatus(code = CREATED)
    @PostMapping
    public Cart create(@RequestBody Cart cart) {
        return cartService.save(cart);
    }
}
