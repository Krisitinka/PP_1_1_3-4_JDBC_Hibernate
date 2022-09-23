package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();

        userDao.saveUser("Kristina", "Belyakova", (byte) 12);
        userDao.saveUser("Dmitrii", "Obyhov", (byte) 22);
        userDao.saveUser("Oleg", "Scirnov", (byte) 53);

        userDao.removeUserById(2);

        System.out.println(userDao.getAllUsers());

        userDao.cleanUsersTable();

    }
}
