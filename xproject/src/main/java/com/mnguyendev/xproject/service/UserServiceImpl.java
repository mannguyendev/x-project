package com.mnguyendev.xproject.service;

import com.mnguyendev.xproject.dao.UserDAO;
import com.mnguyendev.xproject.dao.UserDAOImpl;
import com.mnguyendev.xproject.dao.UserRepository;
import com.mnguyendev.xproject.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserRepository theUserRepository, UserDAO theUserDAO) {
        userRepository = theUserRepository;
        userDAO = theUserDAO;
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }
}
