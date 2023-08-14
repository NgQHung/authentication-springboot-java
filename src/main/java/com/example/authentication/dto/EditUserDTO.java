package com.example.authentication.dto;

import com.example.authentication.model.State;

public record EditUserDTO(String id, String fullName, String email, String password, String state) {
}
