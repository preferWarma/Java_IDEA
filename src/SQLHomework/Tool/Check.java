package SQLHomework.Tool;

import SQLHomework.DataBase.DB;
import SQLHomework.VO.Product;
import SQLHomework.VO.User;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
校验类
 */
public class Check {

    //密码复杂性检验
    public static boolean passwordValid(String password) {
        if (password.length() < 6) {
            System.out.println("密码长度少于6个字符");
            return false;
        } else {
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
            } else {
                System.out.println("没有小写字符");
                return false;
            }
        }
    }

    //校验该用户名和密码是否匹配(传入的参数是未加密的密码)
    public static boolean userCorrect(String userName, String string) throws SQLException, NoSuchAlgorithmException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        //创建语句对象
        String sql = "select password from homework_java.user where userName = ? and password = ?";
        ArrayList<User> arrayList = DB.query(sql, User.class, userName, Encryption.getMD5String(string));
        return arrayList.size() == 1;
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
    public static boolean barCodeExist(String barCode) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        String sql = "select * from homework_java.product where barCode = ?";
        Product product = DB.get(sql, barCode, Product.class);
        return product != null;
    }

    //检查日期格式是否正确
    public static boolean dataCorrect(String date) throws ParseException {
        if (date.length() != 10 || date.charAt(4) != '-' || date.charAt(7) != '-')
            return false;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Date date1 = simpleDateFormat.parse(date);  //按规定格式将String转化为format后的date类型
        return simpleDateFormat.format(date1).equals(date);
    }
}
