package org.msh.fdt.service;

import org.msh.fdt.model.Privilege;
import org.msh.fdt.model.User;

import java.util.List;

/**
 * Created by kenny on 5/28/14.
 */
public interface LoginService {

    public User getUser(String username, String password);

    public User getUser(String username);

    public User getUser(Integer userId);

    public boolean validateSecretQuestion(User user);

    public List<Object[]> getPrivileges(Integer userId);
}
