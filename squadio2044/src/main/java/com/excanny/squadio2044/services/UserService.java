package com.excanny.squadio2044.services;

import com.excanny.squadio2044.dao.RoleDao;
import com.excanny.squadio2044.dao.UserDao;
import com.excanny.squadio2044.models.Role;
import com.excanny.squadio2044.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Normal user role");
        roleDao.save(userRole);

        User adminUser = new User();
        adminUser.setUserId("admin");
        adminUser.setUserName("Admin");
        adminUser.setUserPassword(getEncodedPassword("admin"));
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);

        User user = new User();
        user.setUserId("qbnKddlq70");
        user.setUserName("Mohamed");
        user.setUserPassword(getEncodedPassword("user"));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRole(userRoles);
        userDao.save(user);

        user.setUserId("E1RYSQOjRt");
        user.setUserName("John");
        user.setUserPassword(getEncodedPassword("user"));
        userRoles.add(userRole);
        user.setRole(userRoles);
        userDao.save(user);

        user.setUserId("0PuRdHrJTh");
        user.setUserName("Kumar");
        user.setUserPassword(getEncodedPassword("user"));
        userRoles.add(userRole);
        user.setRole(userRoles);
        userDao.save(user);
    }

//    public User registerNewUser(User user) {
//        Role role = roleDao.findById("User").get();
//        Set<Role> userRoles = new HashSet<>();
//        userRoles.add(role);
//        user.setRole(userRoles);
//        user.setUserPassword(getEncodedPassword(user.getUserPassword()));
//        return userDao.save(user);
//    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}