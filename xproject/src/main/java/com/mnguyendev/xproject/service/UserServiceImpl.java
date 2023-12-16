package com.mnguyendev.xproject.service;

import com.mnguyendev.xproject.dao.UserDAO;
import com.mnguyendev.xproject.dao.UserDAOImpl;
import com.mnguyendev.xproject.dao.UserRepository;
import com.mnguyendev.xproject.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public List<UserEntity> findDisableUsers() {
        return userDAO.findInvalidUser();
    }

    @Override
    public UserEntity updateUser(UserEntity user, int theId) throws Exception {
        UserEntity tempUser = userDAO.findById(theId);
        if (tempUser == null || tempUser.isInvalid()){
            throw new Exception("Could not get user with id = " + theId);
        }
        tempUser.map(user);
        return userDAO.update(tempUser);
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        return userDAO.save(user);
    }

    @Override
    public UserEntity loginUser(String username, String inputPassword) throws Exception {
        UserEntity user = userDAO.findByUsername(username);
        if (user == null || user.isInvalid()){
            throw new Exception("Could not get user with username = " + username);
        }
        boolean isMatched = new BCryptPasswordEncoder().matches(inputPassword, user.getPassword());
            System.out.println(isMatched);
        if (!isMatched){
            throw new Exception("Authentication failed!");
        }

        return user;
    }

    @Override
    public UserEntity findUserById(int theId) throws Exception {
        UserEntity user = userDAO.findById(theId);
        if (user == null || user.isInvalid()){
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

    @Override
    public boolean isAccountExist(UserEntity user) {
        return userDAO.findByUsername(user.getUsername()) != null;
    }
}
