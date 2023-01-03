package com.example.boot.controller;

import java.util.List;

import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.boot.entities.User;
import com.example.boot.exceptions.AuthenticationFailed;
import com.example.boot.exceptions.EntityNotFound;
import com.example.boot.service.UserService;


@RestController
public class UserController {
        
    private static Logger userLogger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ExceptionHandler(AuthenticationFailed.class)
    public ResponseEntity<String> authenticationFailed(AuthenticationFailed e) {
        userLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<String> entityNotFound(EntityNotFound e) {
        userLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<String> sqlIssue(PSQLException e) {
        userLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> deleteFailed(EmptyResultDataAccessException e) {
        userLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>("could not delete user", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/api/user/id/{id}")
    public ResponseEntity<User> findByUserId(@PathVariable int id) {
        return new ResponseEntity<>(this.userService.findUserById(id), HttpStatus.OK);
    }

    @GetMapping("/api/user/{name}")
    public ResponseEntity<User> findByusername(@PathVariable String name) {
        return new ResponseEntity<>(this.userService.findByUsername(name), HttpStatus.OK);
    }

    @GetMapping("/api/user")
    public ResponseEntity<List<User>> findAllUsers() {
        return new ResponseEntity<>(this.userService.findAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/api/user")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        String message = this.userService.createUser(user);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PatchMapping("/api/user")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        return new ResponseEntity<>(this.userService.updateUser(user), HttpStatus.OK);
    }

    @DeleteMapping("/api/user/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable int id) {
        return new ResponseEntity<>(this.userService.deleteUserById(id), HttpStatus.OK);
    }
}
