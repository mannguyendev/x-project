package com.mnguyendev.xproject.service;

import com.mnguyendev.xproject.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> findAll();

    UserEntity save(UserEntity user);

    UserEntity findUserById(int theId) throws Exception;

    int deleteUserById(int theId) throws Exception;

    int softDeleteUserById(int theId) throws Exception;
}
