package org.energygrid.east.authenticationservice.service;

import org.energygrid.east.authenticationservice.model.dto.UserDto;
import org.energygrid.east.authenticationservice.model.fromFrontend.User;
import org.energygrid.east.authenticationservice.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private IAuthenticationService authenticationService;

    @Autowired
    private IJwtService jwtService;

    @Autowired
    private SecurityService securityService;

    @Override
    public String login(User user) throws IllegalAccessException {
        UserDto dbUser = authenticationRepository.findByEmail(user.getEmail());
        if (dbUser == null || securityService.VerifyHash(dbUser.getPassword(), user.getPassword())) {
            throw new IllegalAccessException();
        }

        return jwtService.create(dbUser);
    }
}
