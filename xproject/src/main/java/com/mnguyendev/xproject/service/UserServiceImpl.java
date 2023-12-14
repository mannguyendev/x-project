package com.mnguyendev.xproject.service;

import com.mnguyendev.xproject.dao.UserDAO;
import com.mnguyendev.xproject.dao.UserDAOImpl;
import com.mnguyendev.xproject.dao.UserRepository;
import com.mnguyendev.xproject.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO theUserDAO) {
        userDAO = theUserDAO;
    }

    @Override
    public List<UserEntity> findAll() {
        return userDAO.findAll();
    }

    @Override
    public UserEntity save(UserEntity user) {
        return userDAO.save(user);
    }

    @Override
    public UserEntity findUserById(int theId) throws Exception {
        UserEntity user = userDAO.findById(theId);
        if (user == null){
            throw new Exception("Could not get user with id = " + theId);
        }
        return user;
    }

    @Override
    public int deleteUserById(int theId) throws Exception {
        UserEntity user = userDAO.findById(theId);
        if (user == null){
            throw new Exception("Could not get user with id = " + theId);
        }
        userDAO.deleteById(theId);
        return user.getId();
    }

    @Override
    public int softDeleteUserById(int theId) throws Exception {
        UserEntity user = userDAO.findById(theId);
        if (user == null || user.isInvalid()){
            throw new Exception("Could not get user with id = " + theId);
        }
        user.setDeletedAt(new Date());
        user.setInvalid(true);
        return userDAO.update(user).getId();
    }
}
