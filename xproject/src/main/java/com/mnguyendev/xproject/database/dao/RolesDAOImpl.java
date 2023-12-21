package com.mnguyendev.xproject.database.dao;

import com.mnguyendev.xproject.database.entity.RoleEntity;
import com.mnguyendev.xproject.database.entity.UserEntity;
import com.mnguyendev.xproject.database.entity.UserSectionEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public class RolesDAOImpl implements RolesDAO {
    private EntityManager entityManager;

    String className = UserSectionEntity.class.getName();

    @Autowired
    public RolesDAOImpl(EntityManager theEntityManager){
        this.entityManager = theEntityManager;
    }

    @Override
    @Transactional
    public RoleEntity save(RoleEntity roles) {
        entityManager.persist(roles);
        return roles;
    }

    @Override
    public List<RoleEntity> getRolesOfUser(int theUserId) {
        String query = String.format("from %s where isInvalid = false and userId = :userId", className);

        // create the query
        TypedQuery<RoleEntity> typedQuery = entityManager.createQuery(
                query, RoleEntity.class
        );
        typedQuery.setParameter("userId", theUserId);

        //exec the query
        return typedQuery.getResultList();
    }

    @Override
    @Transactional
    public RoleEntity update(RoleEntity roles) {
        return entityManager.merge(roles);
    }

    @Override
    @Transactional
    public int deleteById(int theId) {
        RoleEntity tempRole = entityManager.find(RoleEntity.class, theId);
        entityManager.remove(tempRole);
        return tempRole.getId();
    }

    @Override
    @Transactional
    public RoleEntity disableById(int theId) {
        RoleEntity tempRole = entityManager.find(RoleEntity.class, theId);
        tempRole.setInvalid(true);
        tempRole.setDeletedAt(new Date());
        return update(tempRole);
    }

    @Override
    public List<RoleEntity> findInvalidRoles() {
        String query = String.format("from %s where isInvalid = true", className);

        // create the query
        TypedQuery<RoleEntity> typedQuery = entityManager.createQuery(
                query, RoleEntity.class
        );

        //exec the query
        return typedQuery.getResultList();
    }
}
