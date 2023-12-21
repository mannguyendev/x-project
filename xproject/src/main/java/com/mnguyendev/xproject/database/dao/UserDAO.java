package com.mnguyendev.xproject.database.dao;

import com.mnguyendev.xproject.database.entity.UserEntity;

import java.util.List;

public interface UserDAO {
    List<UserEntity> findAll();
    List<UserEntity> findInvalidUser();
    UserEntity save(UserEntity user);

    UserEntity findById(int theId);

    UserEntity findByUsername(String username);

    void deleteById(int theId);

    UserEntity update(UserEntity user);
}
