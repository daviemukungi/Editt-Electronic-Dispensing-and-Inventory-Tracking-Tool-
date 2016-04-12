package org.msh.fdt.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.msh.fdt.model.Privilege;
import org.msh.fdt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kenny on 5/28/14.
 */
@Repository
public class LoginDAOImpl implements LoginDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User getUser(String username, String password) {
        Query query = sessionFactory.getCurrentSession().createQuery(" from User WHERE username = '" + username + "' AND password = '" + password + "' ");

        List list = query.list();
        Iterator iterator = list.iterator();

        while(iterator.hasNext()) {
            User u = (User)iterator.next();
            return u;
        }
        return null;
    }

    @Override
    public User getUser(Integer userId) {
        User user = (User)sessionFactory.getCurrentSession().get(User.class, userId);
        return user;
    }

    @Override
    public User getUser(String username) {
        Query query = sessionFactory.getCurrentSession().createQuery(" from User WHERE username = '" + username + "'");

        List list = query.list();
        Iterator iter = list.iterator();

        while(iter.hasNext()) {
            User u = (User)iter.next();
            User user = new User();
            user.setId(u.getId());
            user.setSecretQuestion(u.getSecretQuestion());
            user.setSecretAnswer(u.getSecretAnswer());
            user.setUsername(u.getUsername());
            return user;
        }
        return null;
    }

    @Override
    public List<Object[]> getPrivileges(Integer userId) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT p.name, p.id from privilege p JOIN role_privilege rp ON p.id = rp.privilege_id JOIN role r ON rp.role_id = r.id JOIN user_role ur ON r.id = ur.role_id WHERE ur.user_id = " + userId);
        List<Object[]> privileges = query.list();
        return privileges;
    }
}
