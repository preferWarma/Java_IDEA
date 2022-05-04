package SQLHomework.DAO;

import SQLHomework.DataBase.DB;
import SQLHomework.Tool.Check;
import SQLHomework.VO.Product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class DAOForProduct {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Connection connection = DB.getConnection();
    private static PreparedStatement preparedStatement;

    //使用条形码查找商品
    public static Product getProduct(String barCode) throws SQLException {
        String sql = "select * from homework_java.product where barCode = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, barCode);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Product product = new Product();
            product.setBarCode(resultSet.getString("barCode"));
            product.setProductName(resultSet.getString("productName"));
            product.setPrice(resultSet.getBigDecimal("price"));
            product.setSupply(resultSet.getString("supply"));
            return product;
        }
        else
            return null;
    }

    //根据产品动态数组添加产品
    public static int input(ArrayList<Product> arrayList) throws SQLException {
        String sql = "insert into homework_java.product value (?,?,?,?) ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int ans = 0;
        for (Product product : arrayList) {
            String barCode = product.getBarCode();
            String productName = product.getProductName();
            BigDecimal price = product.getPrice();
            String supply = product.getSupply();
            if (!Check.barCodeExist(barCode)) {
                preparedStatement.setString(1,barCode);
                preparedStatement.setString(2,productName);
                preparedStatement.setBigDecimal(3,price);
                preparedStatement.setString(4,supply);
                ans += preparedStatement.executeUpdate();
            }
        }
        return ans;
    }

    //模糊查询(只需要输入产品名称的部分信息, 如"糖"或"电脑")
    public static void fuzzyQuery() throws SQLException {
        System.out.println("请输入查询的商品名称: ");
        String name = scanner.nextLine();
        name = "%" + name + "%";
        String sql = "select * from homework_java.product where productName like ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,name);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            products.add(new Product(resultSet.getString("barCode"),resultSet.getString("productName"),
                    resultSet.getBigDecimal("price"),resultSet.getString("supply")));
        }
        System.out.println("满足条件的记录总共" + products.size() + "条, 信息如下: ");
        String t = " ";
        System.out.printf("序号%10s条形码%10s商品名称%10s单价%10s供应商\n",t,t,t,t);
        System.out.printf("===%10s=====%10s======%10s====%10s====\n",t,t,t,t);
        int num = 1;
        for (Product product : products) {
            System.out.printf("%d%17s%15s%12s%12s\n",num,product.getBarCode(),product.getProductName(),product.getPrice(),product.getSupply());
            num++;
        }
    }

    //键盘手动添加产品信息
    public static boolean addProductFromConsole() throws SQLException {
        System.out.println("请依次输入商品信息: ");
        System.out.println("商品条形码,商品名称,单价,供应商");
        String[] infos = scanner.nextLine().split("[,，]");
        while (Check.barCodeNotCorrect(infos[0])) {   //条形码格式不对
            System.out.println("条形码格式不对, 请重新输入");
            System.out.println("商品条形码,商品名称,单价,供应商");
            infos = scanner.nextLine().split("[,，]");
        }
        while (Check.barCodeExist(infos[0])) {  //条形码重复
            System.out.println("条形码重复请重新输入");
            System.out.println("商品条形码,商品名称,单价,供应商");
            infos = scanner.nextLine().split("[,，]");
        }
        String sql = "insert into homework_java.product values (?,?,?,?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,infos[0]);
        preparedStatement.setString(2,infos[1]);
        preparedStatement.setBigDecimal(3,BigDecimal.valueOf(Double.parseDouble(infos[2])));
        preparedStatement.setString(4,infos[3]);
        int rolNum = preparedStatement.executeUpdate();
        return rolNum > 0;
    }

}
