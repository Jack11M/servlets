package com.hillel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
    private String oldUsername;
    private String updatedUsername;
    private String firstName;
    private String lastName;
}
