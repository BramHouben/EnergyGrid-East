package org.energygrid.east.userservice.model.viewmodel;

import org.energygrid.east.userservice.model.enums.AccountRole;

public class UserViewModel {
    private String uuid;
    private String username;
    private String email;
    private Enum<AccountRole> accountRole;

    public void setUuid(String id) { this.uuid = id; }
    public String getUuid() { return uuid; }

    public void setUsername(String username) { this.username = username; }
    public String getUsername() { return username; }

    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return email; }

    public void setAccountRole(AccountRole accountRole) { this.accountRole = accountRole; }
    public Enum<AccountRole> getAccountRole() { return accountRole; }
}
