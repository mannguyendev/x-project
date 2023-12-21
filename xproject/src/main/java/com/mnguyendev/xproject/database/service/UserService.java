package com.mnguyendev.xproject.database.service;

import com.mnguyendev.xproject.database.entity.RoleEntity;
import com.mnguyendev.xproject.database.entity.UserEntity;
import com.mnguyendev.xproject.database.entity.UserSectionEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserService {
    List<UserEntity> findAll();
    List<UserEntity> findDisableUsers();
    UserEntity updateUser(UserEntity user, int theId) throws Exception;

    UserSectionEntity createUser(UserEntity user);

    UserSectionEntity loginUser(String username, String inputPassword) throws Exception;

    boolean logoutUser(String token) throws Exception;

    UserEntity findUserById(int theId) throws Exception;

    int deleteUserById(int theId) throws Exception;

    int softDeleteUserById(int theId) throws Exception;

    boolean isAccountExist(UserEntity user);

    UserSectionEntity verifyToken(String token) throws Exception;

    UserSectionEntity auth(HttpServletRequest request) throws Exception;

    UserEntity addRole(int theId, String roleName);

    boolean disableToken(String token);

    boolean disableAllUserTokens(UserEntity user);

    boolean cleanUp();
}
