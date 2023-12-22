package com.mnguyendev.xproject.controller.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.mnguyendev.xproject.common.entities.JsonModel;
import com.mnguyendev.xproject.database.entity.RoleEntity;
import com.mnguyendev.xproject.database.entity.UserEntity;
import com.mnguyendev.xproject.database.entity.UserSectionEntity;
import com.mnguyendev.xproject.database.service.UserSectionService;
import com.mnguyendev.xproject.database.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public Object getAllUser(HttpServletRequest request, HttpServletResponse response){
        JsonModel responseModel = new JsonModel();
        try {
            userService.auth(request);
            return userService.findAll();
        } catch (Exception e){
            log.error(e.getMessage());
            responseModel.put("Error",e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return responseModel.build();
        }
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

            UserSectionEntity userSection = userService.createUser(user);
            userService.addRole(userSection.getUser().getId(), "ROLE_CUSTOMER");
//            List<RoleEntity> roles = new ArrayList<>();
//            roles.add(role);
//            userSection.getUser().setRoles(roles);
            return userSection;
        } catch (Exception e){
            JsonModel responseModel = new JsonModel();
            log.error(e.getMessage());
            responseModel.put("Error",e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return responseModel.build();
        }
    }

    // login user
    @PostMapping("/login")
    public Object loginUser(@RequestBody JsonNode loginInfo, HttpServletResponse response){

        try {
            System.out.println(loginInfo);
            String username = loginInfo.get("username").asText();
            String password = loginInfo.get("password").asText();

            return userService.loginUser(username, password);
        } catch (Exception e){
            JsonModel responseModel = new JsonModel();
            log.error(e.getMessage());
            responseModel.put("Error",e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return responseModel.build();
        }
    }

    @GetMapping("/me/roles")
    public Object getMyRoles(HttpServletRequest request,  HttpServletResponse response){
        JsonModel responseModel = new JsonModel();
        try {
            UserSectionEntity userSection = userService.auth(request);

            return userSection.getUser().getRoles();
        } catch (Exception e){
            log.error(e.getMessage());
            responseModel.put("Error",e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return responseModel.build();
        }
    }

    @GetMapping("/{userId}/roles")
    public Object getRoles(@PathVariable int userId, HttpServletRequest request,  HttpServletResponse response){
        JsonModel responseModel = new JsonModel();
        try {
            UserSectionEntity userSection = userService.auth(request);
            return userService.findUserById(userId).getRoles();
        } catch (Exception e){
            log.error(e.getMessage());
            responseModel.put("Error",e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return responseModel.build();
        }
    }

    @PostMapping("/{theId}/roles")
    public Object addRole(@PathVariable int theId, @RequestBody JsonNode roleInfo, HttpServletRequest request,  HttpServletResponse response){
        JsonModel responseModel = new JsonModel();
        try {
            UserSectionEntity userSection = userService.auth(request);

            String role = roleInfo.get("role").asText();

            return userService.addRole(theId, role);
        } catch (Exception e){
            log.error(e.getMessage());
            responseModel.put("Error",e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return responseModel.build();
        }
    }

    @PostMapping("/me/roles")
    public Object addMyRole(@RequestBody JsonNode roleInfo, HttpServletRequest request,  HttpServletResponse response){
        JsonModel responseModel = new JsonModel();
        try {
            UserSectionEntity userSection = userService.auth(request);

            String role = roleInfo.get("role").asText();

            return userService.addRole(userSection.getUser().getId(), role);
        } catch (Exception e){
            log.error(e.getMessage());
            responseModel.put("Error",e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return responseModel.build();
        }
    }

    @PostMapping("/logout")
    public boolean logoutUser(HttpServletRequest request, HttpServletResponse response){

        try {
            UserSectionEntity userSection = userService.auth(request);
            return userService.disableToken(userSection.getToken());
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
    public UserEntity updateUser(@RequestBody UserEntity user, @PathVariable int theId, HttpServletRequest request, HttpServletResponse response){
        try {
            System.out.println(request);

            return userService.updateUser(user, theId);
        } catch (Exception e){
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }

    // read user
    @GetMapping("/{theId}")
    public Object getUser(@PathVariable int theId, HttpServletRequest request, HttpServletResponse response) {
        JsonModel responseModel = new JsonModel();
        try {
            UserSectionEntity userSection = userService.auth(request);
            System.out.println(userSection);
            return userService.findUserById(theId);
        } catch (Exception e){
            log.error(e.getMessage());
            responseModel.put("Error",e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return responseModel.build();
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
