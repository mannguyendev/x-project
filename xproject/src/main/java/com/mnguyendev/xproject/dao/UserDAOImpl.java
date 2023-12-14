package com.mnguyendev.xproject.dao;

import com.mnguyendev.xproject.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
    public List<UserEntity> findAll() {
        // create the query
        TypedQuery<UserEntity> query = entityManager.createQuery(
                "from UserEntity", UserEntity.class
        );

        //exec the query
        return query.getResultList();
    }

    @Override
    @Transactional
    public UserEntity save(UserEntity user) {
        entityManager.persist(user);
        return user;
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

    @Override
    @Transactional
    public UserEntity update(UserEntity user) {
        return entityManager.merge(user);
    }
}
