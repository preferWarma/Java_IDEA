package SQLHomework.DAO;

import SQLHomework.DataBase.DB;
import SQLHomework.Tool.Check;
import SQLHomework.Tool.Encryption;
import SQLHomework.VO.Product;
import SQLHomework.VO.User;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class DAOForUser {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Connection connection = DB.getConnection();
    private static PreparedStatement preparedStatement;

    //使用用户名和密码查找用户
    public static User getUser(String userName, String password) throws SQLException, NoSuchAlgorithmException {
        String sql = "select * from homework_java.user where userName = ? and password = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userName);
        preparedStatement.setString(2, Encryption.getMD5String(password));
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            User user = new User();
            user.setUserName(resultSet.getString("userName"));
            user.setChrName(resultSet.getString("chrName"));
            user.setPassword(resultSet.getString("password"));
            user.setRole(resultSet.getString("role"));
            return user;
        }
        else
            return null;
    }

    //修改密码
    public static void changePassword(User user) throws SQLException, NoSuchAlgorithmException {
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
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, newPassword);
                statement.setString(2, user.getUserName());
                statement.executeUpdate();
                user.setPassword(newPassword);
                System.out.println("密码已成功修改, 请慎记!");
            }
            else
                System.out.println("密码复杂度不够");
        } else {
            System.out.println("原密码输入不正确, 请重新输入!");
        }
    }

    //收银
    /**
     * 显示“请输入商品条形码（6 位数字字符）：”，用户输入条形码
     * 后，如果输入不是 6 位数字字符，则提示“条形码输入格式不正确，请重新输入”，
     * 直到输入格式正确，如果输入的条形码在商品表中存在，则显示“输入商品数
     * 量：”，否则，提示“您输入的商品条形码不存在，请确认后重新输入”，
     * 条形码和数量输入完毕后在销售明细表中增加一条记录（各字段信息如下：流水
     * 号根据规则生成，条形码为用户输入，商品名称及商品单价是根据输入的条形码
     * 查找商品表获取的，收银员为当前登陆用户名，销售时间为系统当前时间
     * yyyy-mm-dd hh:mm:ss），增加成功后显示“成功增加一笔销售数据”，返回主
     * 菜单。
     */
    public static void payment(User user) throws SQLException {
        System.out.println("请输入商品条形码（6位数字字符）: ");
        String barCode = scanner.nextLine();
        while (true) {
            while (Check.barCodeNotCorrect(barCode)) {
                System.out.println("条形码格式输入不正确, 请重新输入");
                barCode = scanner.nextLine();
            }
            if (Check.barCodeExist(barCode)) {
                break;
            }
            else {
                System.out.println("您输入的商品条形码不存在, 请确认后重新输入");
                barCode = scanner.nextLine();
            }

        }
        Product product = DAOForProduct.getProduct(barCode);
        System.out.println("输入商品数量: ");
        int num = Integer.parseInt(scanner.nextLine());
        String sql = "insert into homework_java.saledetail value (0,?,?,?,?,?,?)";  //流水号自动生成，我们只需要定义一个基准值即可
        preparedStatement = connection.prepareStatement(sql);
        assert product != null;
        preparedStatement.setString(1,product.getBarCode());
        preparedStatement.setString(2, product.getProductName());
        preparedStatement.setBigDecimal(3, product.getPrice());
        preparedStatement.setInt(4,num);
        preparedStatement.setString(5,user.getChrName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        preparedStatement.setString(6, simpleDateFormat.format(new Date()));
        int rolNum = preparedStatement.executeUpdate();
        if (rolNum == 1)
            System.out.println("成功增加一笔销售数据");
    }

    //查询
    /**
     * 显示“请输入销售日期（yyyy-mm-dd）:”，如果日期格式不正确，
     * 则显示“你输入的日期格式不正确，请重新输入”，输入正确后，查询统计当前
     * 日期所有的销售信息，输出显示格式如下
     * */
    public static void statisticsQuery() throws ParseException, SQLException {
        System.out.println("请输入销售日期(yyyy-mm-dd): ");
        String date = scanner.nextLine();
        while (!Check.dataCorrect(date)) {
            System.out.println("你输入的日期格式不正确, 请重新输入");
            date = scanner.nextLine();
        }
        String sql = "select * from homework_java.saledetail where saleTime like ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,date + "%");
        ResultSet resultSet = preparedStatement.executeQuery();
        String year = date.substring(0,4);
        String month = date.substring(5,7);
        String day = date.substring(8,10);
        System.out.println(year + "年 " + month + "月 " + day + "日销售如下:");
        System.out.println("流水号  商品名称   单价    数量    金额             时间             收银员");
        System.out.println("====  =======   ===    ===    ====    ==================       ======");
        int numSum = 0, countSum = 0;
        BigDecimal saleSum = BigDecimal.valueOf(0);
        while (resultSet.next()) {
            numSum++;
            String lsh = resultSet.getString("lsh");
            String productName = resultSet.getString("productName");
            BigDecimal price = resultSet.getBigDecimal("price");
            int count = resultSet.getInt("count");
            countSum += count;
            BigDecimal sum = price.multiply(BigDecimal.valueOf(count));
            saleSum = saleSum.add(sum);
            String saleTime = resultSet.getString("saleTime");
            String chrName = resultSet.getString("operator");
            System.out.printf("%s %8s \t%s  \t%s \t  %s \t %20s\t %5s\n",lsh,productName,price,count,sum,saleTime,chrName);
        }
        System.out.println("销售总数: " + numSum + "\t" + "商品总件: " + countSum + "\t" + "销售总金额: " + saleSum);
        System.out.println("日期: " + year + "年 " + month + "月 " + day + "日");
        System.out.println("请按任意键返回主界面");
        scanner.nextLine();
    }

}
