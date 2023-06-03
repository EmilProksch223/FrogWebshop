package at.technikumwien.webshop.security;

public class UserPrincipal {

    private final Long userId;
    private final String username;
    private final boolean admin;

    // /////////////////////////////////////////////////////////////////////////
    // Init
    // /////////////////////////////////////////////////////////////////////////

    public UserPrincipal(Long userId, String username, boolean admin) {
        this.userId = userId;
        this.username = username;
        this.admin = admin;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    // /////////////////////////////////////////////////////////////////////////

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAdmin() {
        return admin;
    }
}
