package com.mnguyendev.xproject.dao;

import com.mnguyendev.xproject.entity.UserEntity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager theEntityManager){
        this.entityManager = theEntityManager;
    }

    @Override
    @Transactional
    public void save(UserEntity user) {
        entityManager.persist(user);
    }

    @Override
    public UserEntity findById(int theId) {
        return entityManager.find(UserEntity.class, theId);
    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        UserEntity tempUser = entityManager.find(UserEntity.class, theId);
        entityManager.remove(tempUser);
    }
}
