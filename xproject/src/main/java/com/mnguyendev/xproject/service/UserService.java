package com.mnguyendev.xproject.service;

import com.mnguyendev.xproject.entity.UserEntity;
import com.mnguyendev.xproject.entity.UserSectionEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> findAll();
    List<UserEntity> findDisableUsers();
    UserEntity updateUser(UserEntity user, int theId) throws Exception;

    UserEntity createUser(UserEntity user);

    UserSectionEntity loginUser(String username, String inputPassword) throws Exception;

    boolean logoutUser(String token) throws Exception;

    UserEntity findUserById(int theId) throws Exception;

    int deleteUserById(int theId) throws Exception;

    int softDeleteUserById(int theId) throws Exception;

    boolean isAccountExist(UserEntity user);

    UserSectionEntity verifyToken(String token) throws Exception;
}
