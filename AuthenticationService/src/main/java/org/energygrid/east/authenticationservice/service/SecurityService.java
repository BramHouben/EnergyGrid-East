package org.energygrid.east.authenticationservice.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService implements ISecurityService {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String HashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean VerifyHash(String hash, String password) {
        return passwordEncoder.matches(password, hash);
    }
}
