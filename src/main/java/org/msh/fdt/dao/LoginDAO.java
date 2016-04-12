package org.msh.fdt.dao;

import org.msh.fdt.model.Privilege;
import org.msh.fdt.model.User;

import java.util.List;

/**
 * Created by kenny on 5/28/14.
 */
public interface LoginDAO  {

    public User getUser(String username, String password);

    public User getUser(String username);

    public User getUser(Integer userId);

    public List<Object[]> getPrivileges(Integer userId);
}
