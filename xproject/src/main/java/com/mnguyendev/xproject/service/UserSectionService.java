package com.mnguyendev.xproject.service;

import com.mnguyendev.xproject.entity.UserEntity;

public interface UserSectionService {
    UserEntity findUserByToken(String token);

    boolean disableToken(String token);

    boolean disableAllUserTokens(UserEntity user);
}
