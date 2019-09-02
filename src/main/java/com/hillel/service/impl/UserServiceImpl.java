package com.hillel.service.impl;

import com.hillel.dto.UpdateUserDto;
import com.hillel.model.Role;
import com.hillel.model.Status;
import com.hillel.model.User;
import com.hillel.service.UserService;
import com.hillel.storage.UserStorage;

import java.util.Collections;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserStorage userStorage;

    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public boolean usernameExists(String username) {
        User user = getUserByUsername(username);
        return user != null;
    }


    @Override
    public boolean userExistsByNameAndPass(String username, String password) {
        if (usernameExists(username)) {
            User user = getUserByUsername(username);
            return user.getPassword().equals(password);
        }
        return false;
    }

    @Override
    public User getUserByUsername(String username) {
        return userStorage.getUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst().orElse(null);
    }

    @Override
    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    @Override
    public boolean createUser(User user) {
        if (!usernameExists(user.getUsername())) {
            user.setStatus(Status.NOT_LOGGED_IN);
            user.setRoles(Collections.singletonList(Role.USER));
            userStorage.getUsers().add(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(String username) {
        if (usernameExists(username)) {
            userStorage.getUsers().remove(getUserByUsername(username));
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(UpdateUserDto updateUserDto) {
        User newUser = getUserByUsername(updateUserDto.getOldUsername());

        if (newUser != null) {
            String modifiedFirstName = updateUserDto.getFirstName();
            String modifiedLastName = updateUserDto.getLastName();
            String modifiedUsername = updateUserDto.getUpdatedUsername();

            if (modifiedFirstName != null && !modifiedFirstName.equals("")) {
                newUser.setFirstName(modifiedFirstName);
            }
            if (modifiedLastName != null && !modifiedLastName.equals("")) {
                newUser.setLastName(modifiedLastName);
            }
            if (modifiedUsername != null && !usernameExists(modifiedUsername) && !modifiedUsername.equals("")) {
                newUser.setUsername(modifiedUsername);
            }
            return true;
        }
        return false;
    }
}
