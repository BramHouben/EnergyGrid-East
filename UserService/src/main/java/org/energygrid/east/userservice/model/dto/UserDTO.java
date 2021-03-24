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

    public String GetUsername() { return this.username; }
    public void SetUsername(String username) { this.username = username; }

    public String GetEmail() {
        return email;
    }
    public void SetEmail(String email) {
        this.email = email;
    }

    public String GetPassword() {
        return password;
    }
    public void SetPassword(String password) {
        this.password = password;
    }

    public String GetUuid() {
        return uuid;
    }
    public void SetUuid(String userId) {
        this.uuid = userId;
    }

    public AccountRole GetAccountRole() { return accountRole; }
    public void SetAccountRole(AccountRole accountRole) { this.accountRole = accountRole; }
}
