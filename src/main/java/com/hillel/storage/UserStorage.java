package com.hillel.storage;

import com.hillel.model.Role;
import com.hillel.model.Status;
import com.hillel.util.StringConstant;
import lombok.Getter;
import lombok.Setter;
import com.hillel.model.User;

import java.util.*;

@Getter
@Setter
public class UserStorage {
    private List<User> users;

    public UserStorage() {
        users = new ArrayList<>();
        users.add(new User(StringConstant.ADMIN, StringConstant.ADMIN, StringConstant.ADMIN,
                StringConstant.ADMIN, Status.NOT_LOGGED_IN, Arrays.asList(Role.ADMIN, Role.USER)));
        users.add(new User("Jack", "Petrov", "jack", "jack"));
        users.add(new User("Nick", "nick", "nick"));
        users.add(new User("Sergei", "Ivanov", "serg", "serg"));
        users.add(new User("Sergei", "Shevchenko", "serg2", "serg2"));
        users.add(new User("Ivan", "ivan", "ivan"));
    }
}
