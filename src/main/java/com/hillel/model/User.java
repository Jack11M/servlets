package com.hillel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(exclude = {"status"})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Status status;
    private List<Role> roles;

    public User(String firstName, String lastName, String username, String password) {
        this(firstName, username, password);
        this.lastName = lastName;
    }

    public User(String firstName, String username, String password) {
        this.firstName = firstName;
        this.username = username;
        this.lastName = "";
        this.password = password;
        this.status = Status.NOT_LOGGED_IN;
        this.roles = Collections.singletonList(Role.USER);
    }
}
