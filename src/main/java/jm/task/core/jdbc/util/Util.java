package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

import java.util.Properties;

import com.mysql.cj.jdbc.Driver;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;



public class Util {
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "666manamA!";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pp";
    public static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://127.0.0.1:3306/pp_data_base?seLegacyDatetimeCode=false&serverTimezone=Europe/Moscow"); //useSSL=false&amp;&amp;serverTimezone=UTC ?????? useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "mysql");

                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "none"); //none create-drop

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
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