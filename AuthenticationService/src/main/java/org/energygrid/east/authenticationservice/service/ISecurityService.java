package org.energygrid.east.authenticationservice.service;

public interface ISecurityService {
    String HashPassword(String password);

    boolean VerifyHash(String hash, String password);
}
