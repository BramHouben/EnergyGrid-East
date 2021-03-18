package org.energygrid.east.userservice.model.fromFrontend;

import org.energygrid.east.userservice.model.enums.AccountRole;

import java.util.UUID;

public class User {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private Enum<AccountRole> accountRole;

    public void setId(UUID id) { this.id = id; }
    public UUID getId() { return id; }

    public void setUsername(String username) { this.username = username; }
    public String getUsername() { return username; }

    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return email; }

    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return password; }

    public void setAccountRole(AccountRole accountRole) { this.accountRole = accountRole; }
    public Enum<AccountRole> getAccountRole() { return accountRole; }
}
