package at.technikumwien.webshop.service;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import at.technikumwien.webshop.model.User;
import at.technikumwien.webshop.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Service
public class TokenService {

    private static final int EXPIRES_IN = 3600000;

    // TODO: Place into application.properties
    private static final SecretKey JWT_SECRET = Keys.secretKeyFor(HS256);

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    public String generateToken(User user) {
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRES_IN);
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getEncoded());

        return Jwts.builder()
                .claim("id", user.getId())
                .claim("sub", user.getUsername())
                .claim("admin", user.isAdmin())
                .setExpiration(expirationDate)
                .signWith(key, HS256)
                .compact();
    }

    public Optional<UserPrincipal> parseToken(String jwt) {
        Jws<Claims> jwsClaims;

        try {
            jwsClaims = Jwts.parserBuilder()
                    .setSigningKey(JWT_SECRET.getEncoded())
                    .build()
                    .parseClaimsJws(jwt);
        } catch (SignatureException e) {
            return Optional.empty();
        }

        Long userId = jwsClaims.getBody().get("id", Long.class);
        String sub = jwsClaims.getBody().getSubject();
        boolean admin = jwsClaims.getBody().get("admin", Boolean.class);

        return Optional.of(new UserPrincipal(userId, sub, admin));
    }

    public String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
          return bearerToken.substring(7);
        }
        return null;
      }
}
