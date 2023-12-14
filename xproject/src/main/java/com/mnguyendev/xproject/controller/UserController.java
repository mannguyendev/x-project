package com.mnguyendev.xproject.controller;

import com.mnguyendev.xproject.common.GenerateUUID;
import com.mnguyendev.xproject.entity.UserEntity;
import com.mnguyendev.xproject.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService theUserService){
        userService = theUserService;
    }

    // get all users
    @GetMapping("/")
    public List<UserEntity> getAllUser(){
        List<UserEntity> users = userService.findAll();
        log.info(users.toString());
        return users;
    }

    // create user
    @PostMapping("/")
    public UserEntity saveUser(@RequestBody UserEntity user){
        return userService.save(user);
    }

    // update user
//    @PostMapping("/users/:theId")
//    public UserEntity updateUser(@RequestBody UserEntity user, @RequestParam int theId){
//
//
//        return userService.save(user);
//    }

    // read user
    @GetMapping("/{theId}")
    public UserEntity getUser(@PathVariable int theId, HttpServletResponse response) {
        try {
            return userService.findUserById(theId);
        } catch (Exception e){
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }

    // delete user
    @DeleteMapping("/{theId}")
    public int deleteUser(@PathVariable int theId, HttpServletResponse response) {
        try {
            return userService.deleteUserById(theId);
        } catch (Exception e){
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return -1;
        }
    }

    // delete user
    @DeleteMapping("/disable/{theId}")
    public int disableUser(@PathVariable int theId, HttpServletResponse response) {
        try {
            return userService.softDeleteUserById(theId);
        } catch (Exception e){
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return -1;
        }
    }
}
