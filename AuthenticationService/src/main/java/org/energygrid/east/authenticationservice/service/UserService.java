package org.energygrid.east.authenticationservice.service;

import org.energygrid.east.authenticationservice.model.dto.UserDto;
import org.energygrid.east.authenticationservice.model.rabbitmq.UserRabbitMq;
import org.energygrid.east.authenticationservice.repository.AuthenticationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements IUserService {

    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    private ISecurityService securityService;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Override
    public void addUser(UserRabbitMq user) {
        if (authenticationRepository.findByEmail(user.getEmail()) == null) {
            var hashedPassword = securityService.hashPassword(user.getPassword());
            var userToStore = mapper.map(user, UserDto.class);
            userToStore.setPassword(hashedPassword);

            authenticationRepository.save(userToStore);
        }
    }

    @Override
    public void updateUser(UserRabbitMq user) {
        var dbUser = authenticationRepository.findByUuid(user.getUuid());
        if (user.getPassword() != null) {
            String passwordHash = securityService.hashPassword(user.getPassword());
            dbUser.setPassword(passwordHash);
        }

        if (user.getEmail() != null) {
            dbUser.setEmail(user.getEmail());
        }

        authenticationRepository.save(dbUser);
    }

    @Override
    public void deleteUser(UUID uuid) throws IllegalAccessException {
        UserDto userToDelete = authenticationRepository.findByUuid(uuid);
        if (userToDelete == null) {
            throw new IllegalAccessException();
        }

        authenticationRepository.delete(userToDelete);
    }
}
