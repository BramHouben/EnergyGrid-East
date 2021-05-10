package org.energygrid.east.authenticationservice.model.rabbitmq;

import org.energygrid.east.authenticationservice.model.enums.AccountRole;

import java.util.UUID;

public class UserRabbitMq {
    private UUID uuid;
    private String email;
    private String password;
    private AccountRole accountRole;

    public UserRabbitMq() {}

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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public AccountRole getAccountRole() {
        return accountRole;
    }

    public void setAccountRole(AccountRole accountRole) {
        this.accountRole = accountRole;
    }
}
