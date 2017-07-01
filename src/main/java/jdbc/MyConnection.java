package jdbc;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by tan on 2017/1/24.
 */
public class MyConnection {

    /**
     * 获取数据库连接
     *
     * @param type
     *      1、yoomeda正式机连接
     *      2、yoomeda本地连接
     *      3、yoomeda测试机
     * @return
     */
    public static Connection getConn(int type) {
        String driver = "com.mysql.jdbc.Driver";
        String url = "", username = "", password = "";

        switch (type) {
            case 1:
                url = "jdbc:mysql://119.23.207.212:3306/yoomeda?useUnicode=true&characterEncoding=UTF-8";
                username = "root";
                password = "yMei@061706!";
                break;
            case 2:
                url = "jdbc:mysql://localhost:3306/yoomeda_dev?useUnicode=true&characterEncoding=UTF-8";
                username = "root";
                password = "root123";
                break;
            case 3:
                url = "jdbc:mysql://119.23.207.212:3306/yoomeda?useUnicode=true&characterEncoding=UTF-8";
                username = "root";
                password = "yMei@061706!";
                break;
             default:
        }
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
