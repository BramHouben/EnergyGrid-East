package org.energygrid.east.userservice.model.viewmodel;

import org.energygrid.east.userservice.model.enums.AccountRole;

import java.util.UUID;

public class UserViewModel {
    private UUID id;
    private String username;
    private String email;
    private Enum<AccountRole> accountrole;

    public void setId(UUID id) { this.id = id; }
    public UUID getId() { return id; }

    public void setUsername(String username) { this.username = username; }
    public String getUsername() { return username; }

    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return email; }

    public void setAccountrole(AccountRole accountrole) { this.accountrole = accountrole; }
    public Enum<AccountRole> getAccountRole() { return accountrole; }
}