package SQLHomework.Tool;

import SQLHomework.DataBase.DB;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
校验类
 */
public class Check {

    private static final Connection connection = DB.getConnection();
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    //密码复杂性检验
    public static boolean passwordValid(String password) {
        if (password.length() < 6) {
            System.out.println("密码长度少于6个字符");
            return false;
        }
        else {
            boolean flag1 = false;  //是否有大写字符
            boolean flag2 = false;  //是否有小写字符
            for (int i = 0; i < password.length(); i++) {
                if (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z')
                    flag1 = true;
                if (password.charAt(i) >= 'a' && password.charAt(i) <= 'z')
                    flag2 = true;
            }
            if (flag1 && flag2)
                return true;
            else if (!flag1) {
                System.out.println("没有大写字符");
                return false;
            }
            else {
                System.out.println("没有小写字符");
                return false;
            }
        }
    }

    //校验该用户名和密码是否匹配(传入的参数是未加密的密码)
    public static boolean userCorrect(String userName, String string) throws SQLException, NoSuchAlgorithmException {
        //创建语句对象
        String sql = "select password from homework_java.user where userName = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userName);
        //执行
        resultSet = preparedStatement.executeQuery();
        //获取数据库加密密码
        if (resultSet.next()) { //用户名正确
            String password = resultSet.getString("password");
            //比较
            return Encryption.getMD5String(string).equals(password);
        }
        else //用户名不存在
            return false;
    }

    //检验条形码格式是否正确
    public static boolean barCodeNotCorrect(String barCode) {
        for (int i = 0; i < barCode.length(); i++) {
            if (barCode.charAt(i) < '0' || barCode.charAt(i) > '9')
                return true;
        }
        return barCode.length() != 6;
    }

    //检验条形码在商品表是否存在
    public static boolean barCodeExist(String barCode) throws SQLException {
        String sql = "select * from homework_java.product where barCode = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, barCode);
        resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    //检查日期格式是否正确
    public static boolean dataCorrect(String data) throws ParseException {
        if (data.length() != 10 || data.charAt(4)!='-' || data.charAt(7)!='-')
            return false;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Date date1 = simpleDateFormat.parse(data);  //按规定格式将String转化为format类型
        return simpleDateFormat.format(date1).equals(data);
    }

}
