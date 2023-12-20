package com.mnguyendev.xproject.dao;

import com.mnguyendev.xproject.entity.UserEntity;
import com.mnguyendev.xproject.entity.UserSectionEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserSectionDAOImpl implements UserSectionDAO{
    private EntityManager entityManager;

    String className = UserSectionEntity.class.getName();

    @Autowired
    public UserSectionDAOImpl(EntityManager theEntityManager){
        this.entityManager = theEntityManager;
    }

    @Override
    public List<UserSectionEntity> findAll() {
        String query = String.format("from %s where isInvalid = false", className);

        // create the query
        TypedQuery<UserSectionEntity> typedQuery = entityManager.createQuery(
                query, UserSectionEntity.class
        );

        //exec the query
        return typedQuery.getResultList();
    }

    @Override
    @Transactional
    public void save(UserSectionEntity userSection) {
        entityManager.persist(userSection);
    }

    @Override
    public UserSectionEntity findById(int theId) {
        return entityManager.find(UserSectionEntity.class, theId);
    }

    @Override
    public UserSectionEntity findByToken(String token) {
        try {
            String query = String.format("from %s where token = :token", className);

            // create the query
            TypedQuery<UserSectionEntity> typedQuery = entityManager.createQuery(
                    query, UserSectionEntity.class
            );
            typedQuery.setParameter("token", token);

            //exec the query
            return typedQuery.getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<UserSectionEntity> findByUser(UserEntity user) {
        try {
            String query = String.format("from %s where user = :user", className);

            // create the query
            TypedQuery<UserSectionEntity> typedQuery = entityManager.createQuery(
                    query, UserSectionEntity.class
            );
            typedQuery.setParameter("token", user);

            //exec the query
            return typedQuery.getResultList();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        UserSectionEntity tempUserSection = entityManager.find(UserSectionEntity.class, theId);
        entityManager.remove(tempUserSection);
    }

    @Override
    @Transactional
    public UserSectionEntity update(UserSectionEntity userSection) {
        return entityManager.merge(userSection);
    }

    @Override
    public boolean disableToken(String token) {
        UserSectionEntity theSection = this.findByToken(token);
        if (theSection != null){
            theSection.setInvalid(true);
            return this.update(theSection) != null;
        }
        return false;
    }
}
