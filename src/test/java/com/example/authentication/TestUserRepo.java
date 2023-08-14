package com.example.authentication;

import com.example.authentication.model.State;
import com.example.authentication.model.User;
import com.example.authentication.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
//@SpringBootTest
public class TestUserRepo {
    @Test
    public void addUser(){
        UserRepo userRepo = new UserRepo();
        User user = userRepo.addUser("Hung", "hung@hung", "0x-asfdjks133", "PENDING");
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotBlank();
    }

    @Test
    public void addUserWithPendingState(){
        UserRepo userRepo = new UserRepo();
        User user = userRepo.addUser("Hung", "hung@hung", "0x-asfdjks133", "PENDING");
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotBlank();
        assertThat(user.getState()).isEqualTo(State.PENDING);
    }

    @Test
    public void isEmailExist (){
        UserRepo userRepo = new UserRepo();
        userRepo.addUser("hung", "hung@gmail.com", "0x-sdfsdfs23h8");
        userRepo.addUser("Minh Anh", "ma@gmail.com", "0x-sfffsf23");
        userRepo.addUser("Yen Nhi", "yn@gmail.com", "0x-a8u4sdfh0e");
        assertThat(userRepo.isEmailExist("hung@gmail.com")).isTrue();
        assertThat(userRepo.isEmailExist("huNG@gmail.com")).isTrue();
        assertThat(userRepo.isEmailExist("ma@gmail.com")).isTrue();
        assertThat(userRepo.isEmailExist("yn@gmail.com")).isTrue();
        assertThat(userRepo.isEmailExist("ynn@gmail.com")).isFalse();

    }
    @Test
    public void findByEmail() {
        UserRepo userRepo = new UserRepo();
        userRepo.addUser("hung", "hung@gmail.com", "0x-sdfsdfs23h8");
        userRepo.addUser("Minh Anh", "ma@gmail.com", "0x-sfffsf23");
        userRepo.addUser("Yen Nhi", "yn@gmail.com", "0x-a8u4sdfh0e");
        assertThat(userRepo.findByEmail("hung@gmail.com")).isPresent();
        assertThat(userRepo.findByEmail("ma@gmail.com")).isPresent();
        assertThat(userRepo.findByEmail("ma1@gmai.com")).isNotPresent();
    }
    @Test
    public void findById(){
        UserRepo userRepo = new UserRepo();
        User user = userRepo.addUser("hung", "hung@hung.hung", "osnc-8xsdhfow93", "ACTIVE");
        User user_1 = userRepo.addUser("Minh Anh", "ma@ma.ma", "osnc-8xsdh4w93", "PENDING");
        User user_2 = userRepo.addUser("Manh Hung", "mh@mh.mh", "osnc-8xsdh4w93", "ACTIVE");
        Optional<User> userFound = userRepo.findById(user.getId());
        Optional<User> userFound_1 = userRepo.findById(user_1.getId());
        Optional<User> userFound_2 = userRepo.findById(user_2.getId());
        assertThat(userFound).isPresent();
        assertThat(userFound).isNotNull();
        assertThat(userFound_1).isPresent();
        assertThat(userFound_2).isPresent();
        assertThat(userFound.get().getEmail()).isEqualTo("hung@hung.hung");
        assertThat(userFound.get().getId()).isNotBlank();
        assertThat(userFound_1.get().getState()).isEqualTo(State.PENDING);
        assertThat(userFound_2.get().getState()).isNotEqualTo(State.PENDING);
    }
    @Test
    public void updateUserById(){
        UserRepo userRepo = new UserRepo();
        User user = userRepo.addUser("hung", "hung@hung.hung", "osnc-8xsdhfow93", "ACTIVE");
        User new_user = new User(user.getId(),"hung@hung.hung", "hung", "osnc-8xsdhfow93", "PENDING");
        User user_1 = userRepo.addUser("Minh Anh", "ma@ma.ma", "osnc-8xsdh4w93", "PENDING");
            User new_user_1 = new User(user_1.getId(),"hung@hung.hung", "Minh Anh", "osnc-8xsdhfow93", "ACTIVE");

        assertThat(userRepo.updateUserById(new_user)).isNotNull();
        assertThat(userRepo.updateUserById(new_user).getId()).isNotBlank();
        assertThat(userRepo.updateUserById(new_user).getState()).isEqualTo(State.PENDING);
        assertThat(userRepo.updateUserById(new_user_1).getEmail()).isEqualTo("hung@hung.hung");
        assertThat(userRepo.updateUserById(new_user_1).getId()).isNotBlank();
        assertThat(userRepo.updateUserById(new_user_1).getState()).isNotEqualTo(State.PENDING);
    }




}
