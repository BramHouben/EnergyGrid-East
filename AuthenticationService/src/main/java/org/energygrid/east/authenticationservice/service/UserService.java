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
    private final AuthenticationRepository authenticationRepository;

    public UserService(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public void addUser(UserRabbitMq user) {
        if (authenticationRepository.findByEmail(user.getEmail()) == null) {
            var hashedPassword = securityService.HashPassword(user.getPassword());
            var userToStore = mapper.map(user, UserDto.class);
            userToStore.setPassword(hashedPassword);

            authenticationRepository.save(userToStore);
        }
    }

    @Override
    public void updateUser(UserRabbitMq user) {
       authenticationRepository.save(mapper.map(user, UserDto.class));
    }

    @Override
    public void deleteUser(UUID uuid) {
        UserDto userToDelete = authenticationRepository.findByUuid(uuid);
        authenticationRepository.delete(userToDelete);
    }
}
