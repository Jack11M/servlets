package com.hillel.dto;

import com.hillel.model.Role;
import com.hillel.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String firstName;
    private String lastName;
    private String username;
    private Status status;
    private List<Role> roles;
}
