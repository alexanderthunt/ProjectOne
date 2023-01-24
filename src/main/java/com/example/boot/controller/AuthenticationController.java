package com.example.boot.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.boot.entities.User;
import com.example.boot.entities.UserAuthentication;
import com.example.boot.service.UserService;

@RestController
public class AuthenticationController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody UserAuthentication userDetails, HttpSession session) {
        try {
        User UserToAuthenticate = this.userService.findByUsername(userDetails.getUsername());
        session.setAttribute("user", UserToAuthenticate.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserAuthentication user) {
        User UserToRegister = new User();
        UserToRegister.setUsername(user.getUsername());
        UserToRegister.setPassword(user.getPassword());
        this.userService.createUser(UserToRegister);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
/* 
    @PostMapping("/login")
    public String login(HttpSession session){
        session.setAttribute("user", "some username");
        return "logged in successfully";
    }
*/
    @PostMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "logged out";
    }

}

