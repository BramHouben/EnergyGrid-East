package org.energygrid.east.userservice.model.rabbitMq;

import org.energygrid.east.userservice.model.enums.AccountRole;

import java.util.UUID;

public class UserRabbitMq {
    private UUID uuid;
    private String password;
    private String email;
    private AccountRole accountRole;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public AccountRole getAccountRole() {
        return accountRole;
    }

    public void setAccountRole(AccountRole accountRole) {
        this.accountRole = accountRole;
    }
}
