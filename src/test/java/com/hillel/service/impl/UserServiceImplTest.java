package com.hillel.service.impl;

import com.hillel.dto.UpdateUserDto;
import com.hillel.model.Role;
import com.hillel.model.Status;
import com.hillel.model.User;
import com.hillel.storage.UserStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserStorage userStorage;

    @Test
    public void userExistsByNameAndPass_returnsTrueIfUserExists() {
        when(userStorage.getUsers()).thenReturn(getTestUsers());
        boolean actual = userService.userExistsByNameAndPass("admin", "admin");

        assertThat(actual, equalTo(true));
    }

    @Test
    public void userExistsByNameAndPass_returnsFalseIfUserDoesNotExist() {
        when(userStorage.getUsers()).thenReturn(getTestUsers());
        boolean actual = userService.userExistsByNameAndPass("check", "check");

        assertThat(actual, equalTo(false));
    }

    @Test
    public void usernameExists_returnsTrueIfUsernameExists() {
        when(userStorage.getUsers()).thenReturn(getTestUsers());
        boolean actual = userService.usernameExists("admin");

        assertThat(actual, equalTo(true));
    }

    @Test
    public void usernameExists_returnsFalseIfUsernameDoesNotExist() {
        when(userStorage.getUsers()).thenReturn(getTestUsers());
        boolean actual = userService.usernameExists("check");

        assertThat(actual, equalTo(false));
    }

    @Test
    public void getUserByUsername_returnsUserByName() {
        when(userStorage.getUsers()).thenReturn(getTestUsers());
        User expectedUser = new User("admin", "admin", "admin", "admin",
                Status.NOT_LOGGED_IN, Arrays.asList(Role.ADMIN, Role.USER));
        User actualUser = userService.getUserByUsername("admin");

        assertThat(actualUser, equalTo(expectedUser));
    }

    @Test
    public void getUserByUsername_returnsNullIfUserDoesNotExist() {
        when(userStorage.getUsers()).thenReturn(getTestUsers());
        User actualUser = userService.getUserByUsername("check");

        assertThat(actualUser, nullValue());
    }

    @Test
    public void getUsers_returnsListOfUsers() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User("admin", "admin", "admin",
                "admin", Status.NOT_LOGGED_IN, Arrays.asList(Role.ADMIN, Role.USER)));
        expectedUsers.add(new User("Jack", "Petrov", "jack", "jack"));
        when(userStorage.getUsers()).thenReturn(getTestUsers());
        List<User> actualUsers = userService.getUsers();

        assertThat(actualUsers, equalTo(expectedUsers));
    }

    @Test
    public void createUser_returnsTrueIfUserWasCreated() {
        List<User> expectedUsers = getExpectedUsers();
        User addedUser = new User("Boris", "borya", "borya");
        when(userStorage.getUsers()).thenReturn(getTestUsers());
        userService.createUser(addedUser);

        List<User> actualUsers = userService.getUsers();
        boolean actual = actualUsers.equals(expectedUsers);

        assertThat(actual, equalTo(true));
    }

    @Test
    public void createUser_returnsFalseIfUserWasNotCreated() {
        User expectedUser = new User("admin", "admin", "admin", "admin",
                Status.NOT_LOGGED_IN, Arrays.asList(Role.ADMIN, Role.USER));
        when(userStorage.getUsers()).thenReturn(getTestUsers());
        boolean actual = userService.createUser(expectedUser);

        assertThat(actual, equalTo(false));
    }

    @Test
    public void deleteUser_returnsTrueIfUserWasDeleted() {
        User deletedUser = new User("admin", "admin", "admin", "admin",
                Status.NOT_LOGGED_IN, Arrays.asList(Role.ADMIN, Role.USER));
        when(userStorage.getUsers()).thenReturn(getTestUsers());
        userService.deleteUser("admin");

        assertThat(userStorage.getUsers(), not(hasItem(deletedUser)));
    }

    @Test
    public void deleteUser_returnsFalseIfUserWasNotDeleted() {
        when(userStorage.getUsers()).thenReturn(getTestUsers());
        boolean actual = userService.deleteUser("check");

        assertThat(actual, equalTo(false));
    }

    @Test
    public void updateUser_returnsTrueIfUserWasUpdated() {
        UpdateUserDto updateUserDto = new UpdateUserDto("admin", "administrator1", "admin1", "admin1");
        User updatedUser = new User("admin1", "admin1", "administrator1", "admin",
                Status.NOT_LOGGED_IN, Arrays.asList(Role.ADMIN, Role.USER));
        List<User> expectedUsers = getExpectedUsers().stream()
                .map(user -> user.getUsername().equalsIgnoreCase("admin") ? updatedUser : user)
                .collect(Collectors.toList());
        when(userStorage.getUsers()).thenReturn(getExpectedUsers());
        userService.updateUser(updateUserDto);
        List<User> actualUsers = userStorage.getUsers();

        assertThat(actualUsers, equalTo(expectedUsers));
    }


    @Test
    public void updateUser_returnsTrueIfParametersForUpdatingAreNullOrEmtyString() {
        when(userStorage.getUsers()).thenReturn(getTestUsers());
        boolean actual = userService.updateUser(new UpdateUserDto("admin", null, null, ""));

        assertThat(actual, equalTo(true));
    }

    @Test
    public void updateUser_returnsFalseIfUserForUpdatingHasNotExists() {
        UpdateUserDto updateUserDto = new UpdateUserDto("check", "test", "test", "test");
        when(userStorage.getUsers()).thenReturn(getTestUsers());
        boolean actual = userService.updateUser(updateUserDto);

        assertThat(actual, equalTo(false));
    }

    private List<User> getTestUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("admin", "admin", "admin",
                "admin", Status.NOT_LOGGED_IN, Arrays.asList(Role.ADMIN, Role.USER)));
        users.add(new User("Jack", "Petrov", "jack", "jack"));
        return users;
    }

    private List<User> getExpectedUsers() {
        List<User> expectedUsers = getTestUsers();
        expectedUsers.add(new User("Boris", "borya", "borya"));
        return expectedUsers;
    }
}
