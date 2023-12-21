package com.mnguyendev.xproject.database.service;

import com.mnguyendev.xproject.common.CommonUtils;
import com.mnguyendev.xproject.database.dao.RolesDAO;
import com.mnguyendev.xproject.database.dao.UserDAO;
import com.mnguyendev.xproject.database.dao.UserSectionDAO;
import com.mnguyendev.xproject.database.entity.RoleEntity;
import com.mnguyendev.xproject.database.entity.UserEntity;
import com.mnguyendev.xproject.database.entity.UserSectionEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    private UserSectionDAO userSectionDAO;

    private RolesDAO rolesDAO;

    @Autowired
    public UserServiceImpl(UserDAO theUserDAO, UserSectionDAO theUSerSectionDAO, RolesDAO theRolesDAO) {
        userDAO = theUserDAO;
        userSectionDAO = theUSerSectionDAO;
        rolesDAO = theRolesDAO;
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
    public UserSectionEntity createUser(UserEntity user) {
        // save user
        userDAO.save(user);

        // add user token to UserSection table
        UserSectionEntity userSection = new UserSectionEntity(user, CommonUtils.generateJWTCode());
        userSectionDAO.save(userSection);
        return userSection;
    }

    @Override
    public UserSectionEntity loginUser(String username, String inputPassword) throws Exception {
        UserEntity user = userDAO.findByUsername(username);

        if (user == null || user.isInvalid()){
            throw new Exception("Could not get user with username = " + username);
        }
        boolean isMatched = new BCryptPasswordEncoder().matches(inputPassword, user.getPassword());
            System.out.println(isMatched);
        if (!isMatched){
            throw new Exception("Authentication failed!");
        }

        // add user token to UserSection table
        UserSectionEntity userSection = new UserSectionEntity(user, CommonUtils.generateJWTCode());
        userSectionDAO.save(userSection);
        return userSection;
    }

    @Override
    public boolean logoutUser(String token) throws Exception {
        return userSectionDAO.disableToken(token);
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

    @Override
    public UserSectionEntity verifyToken(String token) throws Exception {
        UserSectionEntity userSection = userSectionDAO.findByToken(token);
        if (userSection == null){
            throw new Exception("Could not find user token or expired!");
        }
        return userSection;
    }

    public UserSectionEntity auth(HttpServletRequest request) throws Exception {
        try {
            if (request.getHeader("Authorization") != null) {
                String token = request.getHeader("Authorization").replace("Bearer ", "");
                UserSectionEntity userSection = verifyToken(token);

                if (userSection == null) {
                    throw new Exception("Authorization failed");
                }

                Date createAt = userSection.getCreatedAt();
                Calendar c = Calendar.getInstance();
                c.setTime(createAt);
                c.add(Calendar.YEAR, 1);
                Date expDate = c.getTime();

                Date now = new Date();
                if (expDate.before(now)){
                    userSectionDAO.disableToken(token);
                    throw new Exception("Authentication failed: Your token is expired");
                }

                return userSection;
            } else {
                throw new Exception("Authentication failed: No authentication found in header");
            }
        }catch (Exception e){
            throw new Exception("Authentication failed: "+ e.getMessage());
        }
    }

    @Override
    public UserEntity addRole(int theId, String roleName) {
        RoleEntity roleEntity = new RoleEntity();

        UserEntity user = userDAO.findById(theId);
        user.addRole(roleName);

        return userDAO.save(user);
    }

    @Override
    public boolean disableToken(String token) {
        UserSectionEntity theSection = userSectionDAO.findByToken(token);
        if (theSection != null){
            theSection.setInvalid(true);
            theSection.setDeletedAt(new Date());
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

    @Override
    public boolean cleanUp() {
        List<UserSectionEntity> userSections = userSectionDAO.findInvalidUserSection();
        userSections.forEach(userSection -> userSectionDAO.deleteById(userSection.getId()));
        List<RoleEntity> roleEntities = rolesDAO.findInvalidRoles();
        roleEntities.forEach(role -> rolesDAO.deleteById(role.getId()));
        List<UserEntity> userEntities = userDAO.findInvalidUser();
        userEntities.forEach(user -> userDAO.deleteById(user.getId()));
        return true;
    }
}
