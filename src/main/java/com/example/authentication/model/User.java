package com.example.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

//@Getter
@Data
@AllArgsConstructor
@Builder
public class User {
    private String id;
    private String email;
    private String fullName;
    private String hashedPassword;
    private String state;
}
