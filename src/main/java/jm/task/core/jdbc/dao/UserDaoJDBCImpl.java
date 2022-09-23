package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS `pp`.users ( " +
            "`id` BIGINT(255) NOT NULL AUTO_INCREMENT UNIQUE, " +
            "`name` VARCHAR(128) NULL, " +
            "`last_name` VARCHAR(128) NULL, " +
            "`age` TINYINT(255) NULL, " +
            "PRIMARY KEY (`id`)); " ;
    private static final Connection connection= Util.createConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.execute(CREATE_USERS_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try(Statement statement=connection.createStatement()){
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String s="INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(s)) {
            connection.setAutoCommit(false);
            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setByte(3, age);
            pstmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public void removeUserById(long id) {  //удаление из таблицы Одноого! объекта
        try (Statement statement= connection.createStatement()) {
            String sql="DELETE FROM Table WHERE id ="+id+" ";
            statement.executeUpdate(CREATE_USERS_TABLE);
        }

        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM users")) {
            while(resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("last_name"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }


    public void cleanUsersTable() {
        try (Statement stmt = connection.createStatement()) {
            connection.setAutoCommit(false);
            stmt.executeUpdate("TRUNCATE TABLE users");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
