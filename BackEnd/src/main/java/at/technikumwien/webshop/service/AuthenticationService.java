package at.technikumwien.webshop.service;

import at.technikumwien.webshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    
    private final TokenService tokenService;
    private final UserRepository userRepository;

    // /////////////////////////////////////////////////////////////////////////
    // Init
    // /////////////////////////////////////////////////////////////////////////

    @Autowired
    public AuthenticationService(TokenService tokenService, UserRepository userRepository) {
        this.tokenService =  tokenService;
        this.userRepository = userRepository;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    public String login(String username, String password) {
        var user = userRepository.findByUsernameAndPassword(username, password);

        if (user.isEmpty() || !user.get().isActive()) {
            throw new BadRequestException();
        }

        return tokenService.generateToken(user.get());
    }
}
