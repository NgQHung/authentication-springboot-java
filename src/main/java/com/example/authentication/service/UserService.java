package com.example.authentication.service;

import com.example.authentication.model.User;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;

@Component
public interface UserService {
    public User login(String email, String password) throws InvalidKeySpecException, NoSuchAlgorithmException;
    public boolean logout(String email);
    public User addUser(String fullName, String email, String password) throws InvalidKeySpecException, NoSuchAlgorithmException;
    public User addUserThenAutoActivate(String fullName, String email, String password) throws InvalidKeySpecException, NoSuchAlgorithmException;
    public User activeUser(String id);
    public Boolean updatePassword(String email, String password);
    public void deleteUserById(String id);
    public Optional<User> getUserById(String id);
    public User updateUserById(User user);
    public Boolean updateEmail(String email, String newEmail);
//    public Optional<User> findByEmail(String email);
    public List<User> getListUsers();
}
