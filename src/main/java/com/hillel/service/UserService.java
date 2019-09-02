package com.hillel.service;

import com.hillel.dto.UpdateUserDto;
import com.hillel.model.User;

import java.util.List;

public interface UserService {
    boolean userExistsByNameAndPass(String username, String password);

    boolean usernameExists(String username);

    User getUserByUsername(String username);

    List<User> getUsers();

    boolean createUser(User user);

    boolean deleteUser(String username);

    boolean updateUser(UpdateUserDto updateUserDto);
}
