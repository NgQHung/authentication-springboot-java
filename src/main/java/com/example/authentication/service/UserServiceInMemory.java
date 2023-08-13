package com.example.authentication.service;

import com.example.authentication.exception.UserException;
import com.example.authentication.model.State;
import com.example.authentication.model.User;
import com.example.authentication.repository.UserRepo;
import com.example.authentication.security.Hashing;
import com.example.authentication.security.SHAHash;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class UserServiceInMemory implements UserService {
    private UserRepo userRepo;
    private Hashing hashing;
//    private SHAHash md5;
//    private ConcurrentHashMap<String, String> activate_code_user_id

//    public UserServiceInMemory(UserRepo userRepo, Hashing hashing){
//        this.userRepo = userRepo;
//        this.hashing = hashing;
//    }


    @Override
    public User login(String email, String password) throws InvalidKeySpecException, NoSuchAlgorithmException, UserException {
        Optional<User> o_user = userRepo.findByEmail(email);
        // Check the exist of user
        if(!o_user.isPresent()){
            throw new UserException("User is not found");
        }
        User user = o_user.get();
        // user who want to log in must be state Active
        if(user.getState() != State.ACTIVE){
            throw new UserException("User is not activated");
        }
        // Check the password
        if (hashing.validatePassword(password, o_user.get().getHashedPassword())) {
            return user;
        }else {
            throw new UserException("Password is incorrect");
        }
    }

    @Override
    public boolean logout(String email) {
        return false;
    }

    @Override
    public User addUser(String fullName, String email, String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        boolean isEmailExist = userRepo.isEmailExist(email);
        if(isEmailExist){
            throw new UserException("Email is existed");
        }
        return userRepo.addUser(fullName, email, hashing.hashPassword(password));
    }

    @Override
    public User addUserThenAutoActivate(String fullName, String email, String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return userRepo.addUser(fullName, email, hashing.hashPassword(password), State.ACTIVE);
    }

    @Override
    public Boolean activeUser(String activation_code) {
        return null;
    }

    @Override
    public Boolean updatePassword(String email, String password) {
        return null;
    }

    @Override
    public Boolean updateEmail(String email, String newEmail) {
        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public User findById(String id) {
        return null;
    }
}
