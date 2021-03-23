package org.energygrid.east.userservice.model.dto;

import org.energygrid.east.userservice.model.enums.AccountRole;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserDTO {
    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String uuid;
    private String username;
    private String password;
    private String email;
    private AccountRole accountRole;

    public UserDTO() {}

    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String userId) {
        this.uuid = userId;
    }

    public AccountRole getAccountRole() { return accountRole; }
    public void setAccountRole(AccountRole accountRole) { this.accountRole = accountRole; }
}
