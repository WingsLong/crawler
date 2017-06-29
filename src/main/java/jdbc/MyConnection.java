package jdbc;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by tan on 2017/1/24.
 */
public class MyConnection {

    public static Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
       // String url = "jdbc:mysql://119.23.207.212:3306/yoomeda";
        String url = "jdbc:mysql://localhost:3306/yoomeda_dev?useUnicode=true&characterEncoding=UTF-8";
        String username = "root";
        String password = "root123";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
