package com.mnguyendev.xproject.database.service;

import com.mnguyendev.xproject.database.entity.UserEntity;

public interface UserSectionService {
    UserEntity findUserByToken(String token);

    boolean disableToken(String token);

    boolean disableAllUserTokens(UserEntity user);
}
