package com.mnguyendev.xproject.database.dao;

import com.mnguyendev.xproject.database.entity.RoleEntity;

import java.util.List;

public interface RolesDAO {
    RoleEntity save(RoleEntity roles);

    List<RoleEntity> getRolesOfUser(int theId);

    RoleEntity update(RoleEntity roles);

    int deleteById(int theId);

    RoleEntity disableById(int theId);

    List<RoleEntity> findInvalidRoles();
}
