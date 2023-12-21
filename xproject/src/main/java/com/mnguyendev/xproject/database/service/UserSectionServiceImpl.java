package com.mnguyendev.xproject.database.service;

import com.mnguyendev.xproject.database.dao.UserSectionDAO;
import com.mnguyendev.xproject.database.entity.UserEntity;
import com.mnguyendev.xproject.database.entity.UserSectionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSectionServiceImpl implements UserSectionService {
    private UserSectionDAO userSectionDAO;

    @Autowired
    public UserSectionServiceImpl(UserSectionDAO userSectionDAO){
        this.userSectionDAO =userSectionDAO;
    }

    @Override
    public UserEntity findUserByToken(String token) {
        UserSectionEntity theSection = userSectionDAO.findByToken(token);
        if (theSection != null){
            return theSection.getUser();
        }
        return null;
    }

    @Override
    public boolean disableToken(String token) {
        UserSectionEntity theSection = userSectionDAO.findByToken(token);
        if (theSection != null){
            theSection.setInvalid(true);
            return userSectionDAO.update(theSection) != null;
        }
        return false;
    }

    @Override
    public boolean disableAllUserTokens(UserEntity user) {
        boolean rs = true;
        List<UserSectionEntity> theSections = userSectionDAO.findByUser(user);
        for (UserSectionEntity userSection : theSections){
            userSection.setInvalid(true);
            if (userSectionDAO.update(userSection) == null) rs = false;
        }
        return rs;
    }
}
