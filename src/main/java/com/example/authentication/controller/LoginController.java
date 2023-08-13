package com.example.authentication.controller;

import com.example.authentication.dto.ListUsersDTO;
import com.example.authentication.dto.StateDTO;
import com.example.authentication.dto.UserDTO;
import com.example.authentication.exception.UserException;
import com.example.authentication.model.State;
import com.example.authentication.model.User;
import com.example.authentication.request.LoginRequest;
import com.example.authentication.request.RegisterRequest;
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
import java.util.List;

@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired UserService userService;
    @GetMapping
    public String showHomePage(Model model, HttpSession session){
        UserDTO userDTO =(UserDTO) session.getAttribute("user");

        if(userDTO != null ){
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
    public String handleLogin(@Valid @ModelAttribute LoginRequest loginRequest, BindingResult result, HttpSession session, Model model)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        if(result.hasErrors()){
            return "login";
        }
        User user;
        List<User> listUsers;
        try{
            user = userService.login(loginRequest.email(), loginRequest.password());
            listUsers = userService.getListUsers();
            session.setAttribute("listUsers", new ListUsersDTO(listUsers) );
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
    public String register (Model model){
        model.addAttribute("registerRequest", new RegisterRequest("","",""));
        return "register";
    }

    @PostMapping("register")
    public String register (@Valid @ModelAttribute RegisterRequest registerRequest, BindingResult result, HttpSession session)throws InvalidKeySpecException, NoSuchAlgorithmException {
        if(result.hasErrors()){
            return "register";
        }
        User user;
        try{
            user = userService.addUser(registerRequest.fullName(),registerRequest.email(), registerRequest.password());
            session.setAttribute("user", new UserDTO(user.getId(), user.getFullName(),user.getEmail()));
            return "redirect:/";
        }catch(UserException ex){
            System.out.println(ex.getMessage());
            switch(ex.getMessage()){
                case "Email is existed":
                    result.addError(new FieldError("registerRequest", "email", "Email is existed"));
                    break;
            }
            return "register";
        }
    }

    @GetMapping("admin")
    public String showAdminPage (HttpSession session, Model model){
        UserDTO userDTO =(UserDTO) session.getAttribute("user");
        ListUsersDTO listUsersDTO = (ListUsersDTO) session.getAttribute("listUsers");

        if(userDTO != null){
            model.addAttribute("listUsers", listUsersDTO);
            return "admin";
        } else {
            return "redirect:/";
        }
    }
    @RequestMapping(value="/change-state")
    public String changeState( @RequestParam(value = "action", required = false) String action, HttpSession session) {
        List<User> listUsers;
        userService.activeUser(action);
        listUsers = userService.getListUsers();
        session.setAttribute("listUsers", new ListUsersDTO(listUsers) );
        return "redirect:/admin";
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
