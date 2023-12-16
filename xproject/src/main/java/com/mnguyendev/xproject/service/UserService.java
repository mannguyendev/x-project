package com.mnguyendev.xproject.service;

import com.mnguyendev.xproject.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> findAll();
    List<UserEntity> findDisableUsers();
    UserEntity updateUser(UserEntity user, int theId) throws Exception;

    UserEntity createUser(UserEntity user);

    UserEntity loginUser(String username, String inputPassword) throws Exception;

    UserEntity findUserById(int theId) throws Exception;

    int deleteUserById(int theId) throws Exception;

    int softDeleteUserById(int theId) throws Exception;

    boolean isAccountExist(UserEntity user);
}
