package SQLHomework.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
数据库连接
 */
public class DB {
    //建立连接所需的stack块
    private static Connection connection;
    static {
        //加载MySQL驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //建立与MySQL服务器的连接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306?useUnicode=true&characterEncoding=utf-8","root", "Aa112211");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //返回连接
    public static Connection getConnection() {
        return connection;
    }

    //关闭连接
    public static void closeConnection() throws SQLException {
        if (connection != null)
            connection.close();
    }

}
