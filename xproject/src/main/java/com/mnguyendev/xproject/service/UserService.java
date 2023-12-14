package com.mnguyendev.xproject.service;

import com.mnguyendev.xproject.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> findAll();

    UserEntity save(UserEntity user);
}
