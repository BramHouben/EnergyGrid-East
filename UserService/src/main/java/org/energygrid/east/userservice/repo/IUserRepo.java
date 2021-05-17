package org.energygrid.east.userservice.repo;

import org.energygrid.east.userservice.model.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserRepo extends JpaRepository<UserDTO, String> {
    UserDTO getUserByUuidOrUsernameOrEmail(UUID uuid, String username, String email);
    UserDTO getByUuid(UUID uuid);
}