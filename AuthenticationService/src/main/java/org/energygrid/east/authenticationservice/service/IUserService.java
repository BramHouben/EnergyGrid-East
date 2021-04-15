package org.energygrid.east.authenticationservice.service;

import org.energygrid.east.authenticationservice.model.rabbitmq.UserRabbitMq;

import java.util.UUID;

public interface IUserService {
    void addUser(UserRabbitMq user);
    void updateUser(UserRabbitMq user);
    void deleteUser(UUID uuid) throws IllegalAccessException;
}
