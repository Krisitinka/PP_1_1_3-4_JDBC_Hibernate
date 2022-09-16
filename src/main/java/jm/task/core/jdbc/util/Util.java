package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DB_USERNAME="root";
    private static final String DB_PASSWORD="666manamA!";
    private static final String DB_URL="jdbc:mysql://localhost:3306/pp";
    public static void registerDriver(){
        try{
            Driver driver=new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Connection createConnection(){
        Connection connection=null;
        try{
            connection=DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            connection.setAutoCommit(false);
        }
        catch(SQLException ex){
            System.out.println("Ошибка при создании соединения с БД");
        }

        return connection;
    }
    // реализуйте настройку соеденения с БД
}
