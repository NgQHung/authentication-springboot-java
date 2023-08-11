package com.example.authentication.repository;

import com.example.authentication.model.State;
import com.example.authentication.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepo {
    private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    public User addUser(String fullName, String email, String hashed_password){
        return addUser(fullName, email, hashed_password, State.PENDING);
    }

    public User addUser(String fullName, String email, String hashed_password, State state){
        String id = UUID.randomUUID().toString();
        User user = User.builder()
                .id(id)
                .fullName(fullName)
                .email(email)
                .hashedPassword(hashed_password)
                .state(state)
                .build();
        users.put(id, user);
        return user;
    }
    public boolean isEmailExist(String email) {
     return users.values().stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).count() > 0;
    }


    public Optional<User> findByEmail(String email) {
//        User user = (User) users.values().stream().filter(usr -> usr.getEmail().equalsIgnoreCase(email));
//        System.out.println("user = " + user);
//        return Optional.ofNullable(user);
        return users.values().stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst();
    }
}
