package com.mnguyendev.xproject.dao;

import com.mnguyendev.xproject.entity.UserEntity;

import java.util.List;

public interface UserDAO {

    void save(UserEntity user);

    UserEntity findById(int theId);

    void deleteById(int theId);
}
