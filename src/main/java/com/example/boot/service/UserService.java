package com.example.boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.boot.entities.User;
import com.example.boot.exceptions.EntityNotFound;
import com.example.boot.repository.UserDao;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User findUserById(int id) {
        Optional<User> possibleUser = this.userDao.findById(id);
        if (possibleUser.isPresent()) {
            return possibleUser.get();
        } else {
            throw new EntityNotFound("User not found");
        }
    }

    public User findByUsername(String name) {
        Optional<User> possibleUser = this.userDao.findByUsername(name);
        if (possibleUser.isPresent()) {
            return possibleUser.get();
        } else {
            throw new EntityNotFound("User not found");
        }
    }

    public List<User> findAllUsers() {
        List<User> users = this.userDao.findAll();
        if (users.size() != 0) {
            return users;
        } else {
            throw new EntityNotFound("No Users found in the database.");
        }
    }

    public String createUser(User user) {
        this.userDao.createUser(user.getUsername(), user.getPassword());
        return "User Created";
    }
    
    public String updateUser(User user) {
        int rowCount = this.userDao.updateUser(user.getUsername(), user.getPassword(), user.getId());
        if (rowCount == 1) {
            return "User updated successfully";
        } else {
            throw new EntityNotFound("could not update user");
        }
    }

    public String deleteUserById(int id) {
        this.userDao.deleteById(id);
        return "User with given id deleted.";
    }


}
