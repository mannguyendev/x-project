package com.mnguyendev.xproject.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.mnguyendev.xproject.entity.UserEntity;
import com.mnguyendev.xproject.entity.UserSectionEntity;
import com.mnguyendev.xproject.service.UserSectionService;
import com.mnguyendev.xproject.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    private UserSectionService userSectionService;

    @Autowired
    public UserController(UserService theUserService, UserSectionService theUserSectionService){
        userService = theUserService;
        userSectionService = theUserSectionService;
    }

    // get all users
    @GetMapping("/")
    public List<UserEntity> getAllUser(){
        return userService.findAll();
    }

    // create user
    @PostMapping("/")
    public Object createUser(@RequestBody UserEntity user, HttpServletResponse response){
        try {
            // check user is already exist
            if (userService.isAccountExist(user)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                throw new Exception("Account already exist!");
            }

            return userService.createUser(user);
        } catch (Exception e){
//            JSONObject responseObj = new JSONObject();
//            responseObj.put("Error", e.getMessage());
//            return responseObj.toString();
            return null;
        }
    }

    // login user
    @PostMapping("/login")
    public UserSectionEntity loginUser(@RequestBody JsonNode loginInfo, HttpServletResponse response){

        try {
            System.out.println(loginInfo);
            String username = loginInfo.get("username").asText();
            String password = loginInfo.get("password").asText();

            return userService.loginUser(username, password);
        } catch (Exception e){
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
    }

    @PostMapping("/logout")
    public boolean logoutUser(@RequestBody JsonNode logoutInfo, HttpServletResponse response){

        try {
            System.out.println(logoutInfo);
            String token = logoutInfo.get("token").asText();

            return userService.logoutUser(token);
        } catch (Exception e){
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

    @PostMapping("/verifyToken")
    public UserSectionEntity verifyToken(@RequestBody JsonNode tokenObj, HttpServletResponse response){

        try {
            System.out.println(tokenObj);
            String token = tokenObj.get("token").asText();

            return userService.verifyToken(token);
        } catch (Exception e){
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
    }

    // update user
    @PatchMapping("/{theId}")
    public UserEntity updateUser(@RequestBody UserEntity user, @PathVariable int theId, HttpServletResponse response){
        try {
            return userService.updateUser(user, theId);
        } catch (Exception e){
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }

    // read user
    @GetMapping("/{theId}")
    public UserEntity getUser(@PathVariable int theId, HttpServletResponse response) {
        try {
            UserEntity user = userService.findUserById(theId);
//            System.out.println(user.getUserSections().toString());
            return user;
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

    // disable all user
    @GetMapping("/disable")
    public List<UserEntity> getAllDisableUsers(HttpServletResponse response) {
            return userService.findDisableUsers();
    }
}
