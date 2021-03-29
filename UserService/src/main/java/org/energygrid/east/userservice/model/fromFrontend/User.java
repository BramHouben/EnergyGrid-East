package org.energygrid.east.userservice.model.fromFrontend;

import org.energygrid.east.userservice.model.enums.AccountRole;

import java.util.UUID;

public class User {
    private String uuid;
    private String username;
    private String email;
    private String password;
    private AccountRole accountRole;
    private String language;

    public void SetUuid(String uuid) { this.uuid = uuid; }
    public String GetUuid() { return uuid; }

    public void SetUsername(String username) { this.username = username; }
    public String GetUsername() { return username; }

    public void SetEmail(String email) { this.email = email; }
    public String GetEmail() { return email; }

    public void SetPassword(String password) { this.password = password; }
    public String GetPassword() { return password; }

    public void SetAccountRole(AccountRole accountRole) { this.accountRole = accountRole; }
    public AccountRole GetAccountRole() { return accountRole; }

    public String GetLanguage() { return language; }
    public void SetLanguage(String language) { this.language = language; }
}
