package org.energygrid.east.userservice.model.fromFrontend;

import org.energygrid.east.userservice.model.enums.AccountRole;

import java.util.UUID;

public class User {
    private String uuid;
    private String username;
    private String email;
    private String password;
    private AccountRole accountRole;

    public void setUuid(String uuid) { this.uuid = uuid; }
    public String getUuid() { return uuid; }

    public void setUsername(String username) { this.username = username; }
    public String getUsername() { return username; }

    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return email; }

    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return password; }

    public void setAccountRole(AccountRole accountRole) { this.accountRole = accountRole; }
    public AccountRole getAccountRole() { return accountRole; }
}
