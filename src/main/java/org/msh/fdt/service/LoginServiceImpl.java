package org.msh.fdt.service;

import org.msh.fdt.dao.AdminDAO;
import org.msh.fdt.dao.LoginDAO;
import org.msh.fdt.model.Privilege;
import org.msh.fdt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TableGenerator;
import java.util.List;
import java.util.Random;

/**
 * Created by kenny on 5/28/14.
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginDAO loginDAO;

    @Autowired
    private AdminDAO adminDAO;

    @Override
    @Transactional
    public User getUser(String username, String password) {
        return loginDAO.getUser(username, password);
    }

    @Override
    @Transactional
    public User getUser(Integer userId) {
        return loginDAO.getUser(userId);
    }

    @Override
    @Transactional
    public User getUser(String username) {
        return loginDAO.getUser(username);
    }

    @Override
    @Transactional
    public boolean validateSecretQuestion(User user) {
        User u = loginDAO.getUser(user.getUsername());

        if(u.getSecretAnswer().equalsIgnoreCase(user.getSecretAnswer())) {
            return true;
        } else
            return false;
    }

    @Override
    @Transactional
    public List<Object[]> getPrivileges(Integer userId) {
        return loginDAO.getPrivileges(userId);
    }
}
