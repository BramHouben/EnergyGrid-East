package org.energygrid.east.authenticationservice.service;

import org.energygrid.east.authenticationservice.model.rabbitmq.UserRabbitMq;

public interface IUserService {
    void addUser(String username, String password);
    void updateUser(UserRabbitMq user);
    void deleteUser(String uuid);
}
