package com.example.authentication.controller;

import com.example.authentication.dto.UserDTO;
import com.example.authentication.exception.UserException;
import com.example.authentication.model.State;
import com.example.authentication.model.User;
import com.example.authentication.request.LoginRequest;
import com.example.authentication.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired UserService userService;
    @GetMapping
    public String showHomePage(Model model, HttpSession session){
        UserDTO userDTO =(UserDTO) session.getAttribute("user");
        if(userDTO != null){
            model.addAttribute("user", userDTO);
        }
        return "index";
    }
    @GetMapping("login")
    public String login (Model model){
        model.addAttribute("loginRequest", new LoginRequest("",""));
        return "login";
    }
    @PostMapping("login")
    public String handleLogin(@Valid @ModelAttribute LoginRequest loginRequest, BindingResult result, HttpSession session)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        System.out.println("Result = " +result);
        if(result.hasErrors()){
            return "login";
        }
        User user = new User("","", "", "", State.PENDING);
        try{
            userService.login(loginRequest.email(), loginRequest.password());
            session.setAttribute("user", new UserDTO(user.getId(), user.getFullName(),user.getEmail()));
            return "redirect:/";
        }catch(UserException ex){
            System.out.println(ex.getMessage());
            switch(ex.getMessage()){
                case "User is not found":
                    result.addError(new FieldError("loginRequest", "email", "Email does not exist"));
                    break;
                case "User is not activated":
                    result.addError(new FieldError("loginRequest", "email", "User is not activated"));
                    break;
                case "Password is incorrect":
                    result.addError(new FieldError("loginRequest", "password", "Password is incorrect"));
                    break;
            }
            return "login";
        }
    }


    @GetMapping("register")
    public String register (){
        return "register";
    }

    @GetMapping("admin")
    public String showAdminPage (HttpSession session){

        UserDTO userDTO =(UserDTO) session.getAttribute("user");
        if(userDTO != null){
            return "admin";
        }else {
            return "redirect:/";
        }
    }
    @GetMapping("logout")
    public String logout (HttpSession session){
        session.setAttribute("user", null);
        session.removeAttribute("user ");
        return "redirect:/";
    }

    @GetMapping("foo")
    public String foo() {
        throw new UserException("Some error");
    }


}
