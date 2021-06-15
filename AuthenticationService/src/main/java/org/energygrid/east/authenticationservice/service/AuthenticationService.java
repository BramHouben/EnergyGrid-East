package org.energygrid.east.authenticationservice.service;

import org.energygrid.east.authenticationservice.model.dto.UserDto;
import org.energygrid.east.authenticationservice.model.fromfrontend.User;
import org.energygrid.east.authenticationservice.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private IJwtService jwtService;

    @Autowired
    private SecurityService securityService;

    @Override
    public String login(User user) throws IllegalAccessException {
        UserDto dbUser = authenticationRepository.findByEmail(user.getEmail());
        if (dbUser == null || !securityService.verifyHash(dbUser.getPassword(), user.getPassword())) {
            throw new IllegalAccessException();
        }

        return jwtService.create(dbUser);
    }
}
