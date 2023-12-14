package com.mnguyendev.xproject.dao;

import com.mnguyendev.xproject.entity.UserEntity;

import java.util.List;

public interface UserDAO {
    List<UserEntity> findAll();

    UserEntity save(UserEntity user);

    UserEntity findById(int theId);

    void deleteById(int theId);

    UserEntity update(UserEntity user);
}
