package com.hillel.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillel.dto.DeleteUserDto;
import com.hillel.dto.UpdateUserDto;
import com.hillel.model.User;

import java.io.IOException;
import java.util.List;

public class JsonUtil {
    private ObjectMapper mapper = new ObjectMapper();

    public User jsonReaderInUser(String json) throws IOException {
        return mapper.readValue(json, User.class);
    }

    public String jsonWriterFromUser(User user) throws JsonProcessingException {
        return mapper.writeValueAsString(user);
    }

    public String jsonWriterFromListOfUsers(List<User> users) throws JsonProcessingException {
        return mapper.writeValueAsString(users);
    }

    public UpdateUserDto jsonReaderInUpdateUserDto(String json) throws IOException {
        return mapper.readValue(json, UpdateUserDto.class);
    }

    public DeleteUserDto jsonReaderInDeleteUserDto(String json) throws IOException {
        return mapper.readValue(json, DeleteUserDto.class);
    }
}
