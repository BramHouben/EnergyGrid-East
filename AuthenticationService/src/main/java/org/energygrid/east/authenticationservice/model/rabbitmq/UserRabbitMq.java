package org.energygrid.east.authenticationservice.model.rabbitmq;

import java.util.UUID;

public class UserRabbitMq {
    private UUID uuid;
    private String email;
    private String password;

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
}
