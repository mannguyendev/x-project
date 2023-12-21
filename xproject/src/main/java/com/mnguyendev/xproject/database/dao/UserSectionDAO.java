package com.mnguyendev.xproject.database.dao;

import com.mnguyendev.xproject.database.entity.UserEntity;
import com.mnguyendev.xproject.database.entity.UserSectionEntity;

import java.util.List;

public interface UserSectionDAO {
    List<UserSectionEntity> findAll();
    void save(UserSectionEntity userSection);

    UserSectionEntity findById(int theId);

    UserSectionEntity findByToken(String token);

    List<UserSectionEntity> findByUser(UserEntity user);

    void deleteById(int theId);

    UserSectionEntity update(UserSectionEntity userSection);

    boolean disableToken(String token);

    List<UserSectionEntity> findInvalidUserSection();
}
