package SQLHomework.DAO;

import SQLHomework.DataBase.DB;
import SQLHomework.Tool.Check;
import SQLHomework.Tool.Encryption;
import SQLHomework.VO.Product;
import SQLHomework.VO.SaleDetail;
import SQLHomework.VO.User;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class DAOForUser {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Connection connection = DB.getConnection();
    private static PreparedStatement preparedStatement;

    //使用用户名和密码查找用户
    public static User getUser(String userName, String password) throws SQLException, NoSuchAlgorithmException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        String sql = "select * from homework_java.user where userName = ? and password = ?";
        ArrayList<User> arrayList = DB.query(sql, User.class, userName, Encryption.getMD5String(password));
        if (arrayList.size() > 0)
            return arrayList.get(0);
        else
            return null;
    }

    //修改密码
    public static void changePassword(@NotNull User user) throws SQLException, NoSuchAlgorithmException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        System.out.println("请输入当前用户的原密码: ");
        String oldPassword = scanner.nextLine();
        if (Check.userCorrect(user.getUserName(), oldPassword)) {
            System.out.println("请输入新密码: ");
            String newPassword = scanner.nextLine();
            System.out.println("请输入确认密码: ");
            String confirmPassword = scanner.nextLine();
            while (!newPassword.equals(confirmPassword)) {
                System.out.println("两次输入的密码必须一致, 请重新输入确认密码: ");
                confirmPassword = scanner.nextLine();
            }
            if (Check.passwordValid(newPassword)) {
                newPassword = Encryption.getMD5String(newPassword);
                String sql = "update homework_java.user set password = ? where userName = ?";
                DB.update(sql, newPassword, user.getUserName());
                user.setPassword(newPassword);
                System.out.println("密码已成功修改, 请慎记!");
            } else
                System.out.println("密码复杂度不够");
        } else {
            System.out.println("原密码输入不正确, 请重新输入!");
        }
    }

    //收银
    public static void payment(User user) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        System.out.println("请输入商品条形码（6位数字字符）: ");
        String barCode = scanner.nextLine();
        while (true) {
            while (Check.barCodeNotCorrect(barCode)) {
                System.out.println("条形码格式输入不正确, 请重新输入");
                barCode = scanner.nextLine();
            }
            if (Check.barCodeExist(barCode)) {
                break;
            } else {
                System.out.println("您输入的商品条形码不存在, 请确认后重新输入");
                barCode = scanner.nextLine();
            }

        }
        Product product = DAOForProduct.getProduct(barCode);
        System.out.println("输入商品数量: ");
        int num = Integer.parseInt(scanner.nextLine());
        String sql = "insert into homework_java.saledetail value (?,?,?,?,?,?,?)";  //流水号自动生成，我们只需要定义一个基准值即可
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        int rolNum = DB.update(sql, DAOForSaleDetail.createNumber(), product.getBarCode(), product.getProductName(), product.getPrice(), num, user.getChrName(), simpleDateFormat.format(new Date()));
        if (rolNum == 1)
            System.out.println("成功增加一笔销售数据");
    }

    //查询
    public static void statisticsQuery() throws ParseException, SQLException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        System.out.println("请输入销售日期(yyyy-mm-dd): ");
        String date = scanner.nextLine();
        while (!Check.dataCorrect(date)) {
            System.out.println("你输入的日期格式不正确, 请重新输入");
            date = scanner.nextLine();
        }
        String sql = "select * from homework_java.saledetail where saleTime like ?";
        ArrayList<SaleDetail> arrayList = DB.query(sql, SaleDetail.class, date + "%");
        String year = date.substring(0, 4);
        String month = date.substring(5, 7);
        String day = date.substring(8, 10);
        System.out.println(year + "年 " + month + "月 " + day + "日销售如下:");
        System.out.println("    流水号        商品名称     单价    数量    金额             时间             收银员");
        System.out.println("=============   =======     ===    ===    ====    ==================       ======");
        int numSum = 0, countSum = 0;
        BigDecimal saleSum = BigDecimal.valueOf(0);
        for (SaleDetail saleDetail : arrayList) {
            numSum++;
            String lsh = saleDetail.getLsh();
            String productName = saleDetail.getProductName();
            BigDecimal price = saleDetail.getPrice();
            int count = saleDetail.getCount();
            countSum += count;
            BigDecimal sum = price.multiply(BigDecimal.valueOf(count));
            saleSum = saleSum.add(sum);
            String saleTime = saleDetail.getSaleTime();
            String chrName = saleDetail.getOperator();
            System.out.printf("%s %6s \t%8s  \t%s \t  %s \t %20s\t %5s\n", lsh, productName, price, count, sum, saleTime, chrName);
        }
        System.out.println("销售总数: " + numSum + "\t" + "商品总件: " + countSum + "\t" + "销售总金额: " + saleSum);
        System.out.println("日期: " + year + "年 " + month + "月 " + day + "日");
        System.out.println("请按任意键返回主界面");
        scanner.nextLine();
    }

}
