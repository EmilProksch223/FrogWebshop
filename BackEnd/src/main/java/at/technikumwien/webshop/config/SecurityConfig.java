package at.technikumwien.webshop.config;

import at.technikumwien.webshop.security.AuthenticationFilter;
import at.technikumwien.webshop.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final TokenService tokenService;

    // /////////////////////////////////////////////////////////////////////////
    // Init
    // /////////////////////////////////////////////////////////////////////////

    public SecurityConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // Disable csrf
        httpSecurity.csrf().disable()
                // Enable cors
                .cors()
                .and()
                // Set session management to stateless
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Allow unauthorized requests to certain endpoints
                .authorizeHttpRequests()
                //admin requests
                .requestMatchers(HttpMethod.POST, "/products", "/users/update", "/files").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users", "/products").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/users/update", "/products/update", "/files/update").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/{id}", "/products/{id}").hasRole("ADMIN")
                //all user requests
                .requestMatchers(HttpMethod.POST, "/users/createUser", "/positions").permitAll()
                .requestMatchers(HttpMethod.GET, "/files/**", "/products/active", "/{cartId}/positions").permitAll()
                .requestMatchers("/login", "/addresses", "/addresses/users/**", "/users/{id}/address").permitAll()

                // Authenticate all other requests
                .anyRequest().authenticated()
                .and()
                // Add filter to validate tokens with every request
                .addFilterBefore(new AuthenticationFilter(tokenService),
                        UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
