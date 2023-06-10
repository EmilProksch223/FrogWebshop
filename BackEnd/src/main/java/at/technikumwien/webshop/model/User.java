package at.technikumwien.webshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

<<<<<<< HEAD
=======
    @Column(name = "email", nullable = false)
    private String email;

>>>>>>> 2830e02aeb93e34278cb00e67bfd5b8d5777cc2e
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "admin", nullable = false)
    private boolean admin;

    // /////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    // /////////////////////////////////////////////////////////////////////////

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

<<<<<<< HEAD
=======
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

>>>>>>> 2830e02aeb93e34278cb00e67bfd5b8d5777cc2e
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
