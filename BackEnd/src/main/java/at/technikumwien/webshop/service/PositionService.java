package at.technikumwien.webshop.service;

import java.util.List;
import java.util.Optional;

import at.technikumwien.webshop.model.Cart;
import at.technikumwien.webshop.model.Position;
import at.technikumwien.webshop.model.Product;
import at.technikumwien.webshop.model.User;
import at.technikumwien.webshop.repository.PositionRepository;
import at.technikumwien.webshop.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class PositionService {

    private final PositionRepository positionRepository;
    private final UserRepository userRepository;

    private final CartService cartService;
    private final ProductService productService;

    private final TokenService tokenService;

    // /////////////////////////////////////////////////////////////////////////
    // Init
    // /////////////////////////////////////////////////////////////////////////

    public PositionService(PositionRepository positionRepository,
            UserRepository userRepository,
            CartService cartService,
            ProductService productService,
            TokenService tokenService) {
        this.positionRepository = positionRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.productService = productService;
        this.tokenService = tokenService;
    }

    /////
    // Methods
    /////

    public Optional<Position> findById(Long id) {
        return positionRepository.findById(id);
    }

    public List<Position> findByCartId(Long id) {
        return positionRepository.findByCartId(id);
    }

    public Position save(Position position, Long productId, String token) {

        Long userId = tokenService.getUserIdFromToken(token);

        Cart cart = cartService.findByUserId(userId);

        if (cart == null) {
            Optional<User> user = userRepository.findById(userId);

            if (user.isPresent()) {
                cart = cartService.save(new Cart(user.get()));
            } else {
                throw new RuntimeException("User does not exist");
            }
        }

        Optional<Product> product = productService.getProductById(productId);

        if (product.isEmpty()) {
            throw new RuntimeException("Product does not exist");
        }

        Position existingPosition = positionRepository.findByCartAndProduct(cart, product.get());

        if (existingPosition != null) {
            existingPosition.setQuantity(existingPosition.getQuantity() + 1);
            return positionRepository.save(existingPosition);
        } else {
            position.setCart(cart);
            position.setProduct(product.get());
            return positionRepository.save(position);
        }
    }

    public void deletePosition(Long id, String token) {
        Long userId = tokenService.getUserIdFromToken(token);
        Long cartId = cartService.findByUserId(userId).getId();
        positionRepository.deleteByIdAndCartId(id, cartId);
    }

    public void deletePositionWithCardId(Long id) {
        positionRepository.deleteByCartId(id);
    }
}
