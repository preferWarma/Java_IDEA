package SQLHomework.DataBase;

import java.lang.reflect.Field;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/*
数据库连接
 */
public class DB {
    //建立连接所需的stack块
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    static {
        //加载MySQL驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //建立与MySQL服务器的连接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306?useUnicode=true&characterEncoding=utf-8", "root", "Aa112211");
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
        if (preparedStatement != null)
            preparedStatement.close();
        if (resultSet != null)
            resultSet.close();
    }

    //查询(泛型编程)
    public static <T> ArrayList<T> query(String sql, Class<T> cls, Object... params) throws SQLException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        ArrayList<T> arrayList = new ArrayList<>();
        preparedStatement = connection.prepareStatement(sql);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setString(i + 1, String.valueOf(params[i]));
            }
        }
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            T obj = cls.newInstance();    //创建对象
            //获取查询结果的所有字段
            ResultSetMetaData data = resultSet.getMetaData();   //记录集的源数据
            for (int i = 1; i <= data.getColumnCount(); i++) {
                String fieldName = data.getColumnName(i);   //获取字段名
                Object fieldValue;  //字段值
                if (fieldName.equals("saleTime")) { //特判DateTime类型
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                    fieldValue = simpleDateFormat.format(resultSet.getTimestamp(fieldName));
                } else
                    fieldValue = resultSet.getObject(fieldName);
                Field field = cls.getDeclaredField(fieldName);  //获取字段
                field.setAccessible(true);
                field.set(obj, fieldValue);
            }
            arrayList.add(obj);
        }
        return arrayList;
    }

    //插入
    public static int update(String sql, Object... params) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        if (params == null)
            return 0;
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setString(i + 1, String.valueOf(params[i]));
        }
        return preparedStatement.executeUpdate();
    }

    //通过主键获取(泛型编程)
    public static <T> T get(String sql, String keyValue, Class<T> cls) throws SQLException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        T obj = null;
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, keyValue);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            obj = cls.newInstance();    //创建对象
            //获取查询结果的所有字段
            ResultSetMetaData data = resultSet.getMetaData();   //记录集的源数据
            for (int i = 1; i <= data.getColumnCount(); i++) {
                String fieldName = data.getColumnName(i);   //获取字段名
                Object fieldValue = resultSet.getObject(fieldName); //获取字段值
                Field field = cls.getDeclaredField(fieldName);  //获取字段
                field.setAccessible(true);
                field.set(obj, fieldValue);
            }
        }
        return obj;
    }

}
