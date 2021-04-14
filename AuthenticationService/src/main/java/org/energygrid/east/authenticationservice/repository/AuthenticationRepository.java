package org.energygrid.east.authenticationservice.repository;

import org.energygrid.east.authenticationservice.model.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthenticationRepository extends JpaRepository<UserDto, Integer> {
    UserDto findByEmail(String email);
    UserDto findByUuid(UUID uuid);
}
