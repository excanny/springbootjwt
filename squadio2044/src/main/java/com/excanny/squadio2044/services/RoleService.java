package com.excanny.squadio2044.services;

import com.excanny.squadio2044.dao.RoleDao;
import com.excanny.squadio2044.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role createNewRole(Role role) {
        return roleDao.save(role);
    }
}