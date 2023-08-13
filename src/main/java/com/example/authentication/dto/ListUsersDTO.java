package com.example.authentication.dto;

import com.example.authentication.model.User;

import java.util.ArrayList;
import java.util.List;

public record ListUsersDTO(List<User> listUsers) {
    public ListUsersDTO(List<User> listUsers){
        this.listUsers = listUsers;
    }
}
