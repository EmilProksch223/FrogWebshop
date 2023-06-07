package at.technikumwien.webshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO for {@link User}
 */
public class UserDTO {

    private Long id;

    @NotBlank(message = "Benutzername darf nicht leer sein")
    private String username;

    @NotBlank(message = "Passwort darf nicht leer sein")
    @Size(min = 8, message = "Passwort muss mindestens 8 Zeichen lang sein")
    private String password;

    @NotNull(message = "admin darf nicht null sein")
    private Boolean admin;

     // /////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    // /////////////////////////////////////////////////////////////////////////
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

}
