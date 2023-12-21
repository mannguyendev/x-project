package com.mnguyendev.xproject.controller;

import com.mnguyendev.xproject.database.entity.UserEntity;
import com.mnguyendev.xproject.database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class HomeController {

    private UserService userService;

    @Autowired
    public HomeController(UserService theUserService){
        userService = theUserService;
    }

    @GetMapping("/")
    public String homeMapping(){
        return "Hello x-project";
    }

    @GetMapping("/data")
    public List<UserEntity> dataMapping(){
        return userService.findAll();
    }

    @GetMapping("/save")
    public Object saveUser(){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("man");
        userEntity.setCreatedAt(new Date());
        userService.createUser(userEntity);
        return userEntity;
    }

    @PostMapping("/cleanup")
    public Object cleanup(){
        return userService.cleanUp();
    }
}
