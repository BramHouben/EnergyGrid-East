package org.energygrid.east.authenticationservice.service;

import org.energygrid.east.authenticationservice.model.User;
import org.energygrid.east.authenticationservice.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Override
    public void addUser(String email, String password) {
        if (authenticationRepository.findUserByEmail(email) == null) {
            var hashedPassword = passwordEncoder.encode(password);
            var user = new User();
            user.setEmail(email);
            user.setPassword(hashedPassword);

            authenticationRepository.save(user);
        }
    }
}
