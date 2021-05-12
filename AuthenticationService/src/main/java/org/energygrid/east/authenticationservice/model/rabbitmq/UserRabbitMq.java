package org.energygrid.east.authenticationservice.model.rabbitmq;

import java.util.UUID;

public class UserRabbitMq {
    private UUID uuid;
    private String email;
    private String password;
    private org.energygrid.east.authenticationservice.model.enums.accountRole accountRole;
    
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
    public org.energygrid.east.authenticationservice.model.enums.accountRole getAccountRole() {
        return accountRole;
    }

    public void setAccountRole(org.energygrid.east.authenticationservice.model.enums.accountRole accountRole) {
        this.accountRole = accountRole;
    }
}
