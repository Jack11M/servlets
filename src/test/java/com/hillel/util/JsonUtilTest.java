package com.hillel.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hillel.dto.DeleteUserDto;
import com.hillel.dto.UpdateUserDto;
import com.hillel.model.User;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class JsonUtilTest {
    private JsonUtil jsonUtil;

    @Before
    public void setUp() {
        jsonUtil = new JsonUtil();
    }

    @Test
    public void jsonReaderInUser_throwsIOException_returnsUser() throws IOException {
        User expectedUser = new User("Sergei", "Ivanov", "serg", "serg");
        String json = "{\"firstName\":\"Sergei\",\"lastName\":\"Ivanov\",\"username\":\"serg\","
                + "\"password\":\"serg\",\"status\":\"NOT_LOGGED_IN\",\"roles\":[\"USER\"]}";
        User actualUser = jsonUtil.jsonReaderInUser(json);

        assertThat(actualUser, equalTo(expectedUser));
    }

    @Test
    public void jsonWriterFromUser_throwsJsonProcessingException_returnsJson() throws JsonProcessingException {
        User user = new User("Sergei", "Ivanov", "serg", "serg");
        String expectedJson = "{\"firstName\":\"Sergei\",\"lastName\":\"Ivanov\",\"username\":\"serg\","
                + "\"password\":\"serg\",\"status\":\"NOT_LOGGED_IN\",\"roles\":[\"USER\"]}";
        String actualJson = jsonUtil.jsonWriterFromUser(user);

        assertThat(actualJson, equalTo(expectedJson));
    }

    @Test
    public void jsonWriterFromListOfUsers_throwsJsonProcessingException_returnsJson() throws JsonProcessingException {
        List<User> users = new ArrayList<>();
        users.add(new User("Nick", "nick", "nick"));
        users.add(new User("Ivan", "ivan", "ivan"));
        users.add(new User("Sergei", "Ivanov", "serg", "serg"));
        String expectedListOfJson = "[{\"firstName\":\"Nick\",\"lastName\":\"\",\"username\":\"nick\""
                + ",\"password\":\"nick\",\"status\":\"NOT_LOGGED_IN\",\"roles\":[\"USER\"]},{\"firstName\":\"Ivan\""
                + ",\"lastName\":\"\",\"username\":\"ivan\",\"password\":\"ivan\",\"status\":\"NOT_LOGGED_IN\",\"roles\":[\"USER\"]}"
                + ",{\"firstName\":\"Sergei\",\"lastName\":\"Ivanov\",\"username\":\"serg\",\"password\""
                + ":\"serg\",\"status\":\"NOT_LOGGED_IN\",\"roles\":[\"USER\"]}]";
        String actualListOfJson = jsonUtil.jsonWriterFromListOfUsers(users);

        assertThat(actualListOfJson, equalTo(expectedListOfJson));
    }

    @Test
    public void jsonReaderInUpdateUserDto_throwsIOException_returnsUpdateUserDto() throws IOException {
        UpdateUserDto expectedUpdateUserDto = new UpdateUserDto("serg", "test", "test", "test");
        String json = "{\"firstName\":\"test\",\"lastName\":\"test\",\"updatedUsername\":\"test\",\"oldUsername\":\"serg\"}";
        UpdateUserDto actualUpdateUserDto = jsonUtil.jsonReaderInUpdateUserDto(json);

        assertThat(actualUpdateUserDto, equalTo(expectedUpdateUserDto));
    }

    @Test
    public void jsonReaderInInDeleteUserDto_throwsIOException_returnsDeleteUserDto() throws IOException {
        DeleteUserDto expectedDeleteUserDto = new DeleteUserDto("test");
        String json = "{\"username\":\"test\"}";
        DeleteUserDto actualDeleteUserDto = jsonUtil.jsonReaderInDeleteUserDto(json);

        assertThat(actualDeleteUserDto, equalTo(expectedDeleteUserDto));
    }
}
