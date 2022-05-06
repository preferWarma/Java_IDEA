package SQLHomework.DAO;

import SQLHomework.DataBase.DB;
import SQLHomework.Tool.Check;
import SQLHomework.VO.Product;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class DAOForProduct {

    private static final Scanner scanner = new Scanner(System.in);

    //使用条形码查找商品
    public static Product getProduct(String barCode) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        String sql = "select * from homework_java.product where barCode = ?";
        return DB.get(sql, barCode, Product.class);
    }

    //根据产品动态数组添加产品
    public static int input(ArrayList<Product> arrayList) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        String sql = "insert into homework_java.product value (?,?,?,?) ";
        int ans = 0;
        for (Product product : arrayList) {
            String barCode = product.getBarCode();
            String productName = product.getProductName();
            BigDecimal price = product.getPrice();
            String supply = product.getSupply();
            if (!Check.barCodeExist(barCode)) {
                ans += DB.update(sql, barCode, productName, price, supply);
            }
        }
        return ans;
    }

    //模糊查询(只需要输入产品名称的部分信息, 如"糖"或"电脑")
    public static void fuzzyQuery() throws SQLException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        System.out.println("请输入查询的商品名称: ");
        String name = scanner.nextLine();
        name = "%" + name + "%";
        String sql = "select * from homework_java.product where productName like ?";
        ArrayList<Product> products = DB.query(sql, Product.class, name);
        System.out.println("满足条件的记录总共" + products.size() + "条, 信息如下: ");
        String t = " ";
        System.out.printf("序号%10s条形码%10s商品名称%10s单价%10s供应商\n", t, t, t, t);
        System.out.printf("===%10s=====%10s======%10s====%10s====\n", t, t, t, t);
        int num = 1;
        for (Product product : products) {
            System.out.printf("%d%17s%15s%12s%12s\n", num, product.getBarCode(), product.getProductName(), product.getPrice(), product.getSupply());
            num++;
        }
    }

    //键盘手动添加产品信息
    public static boolean addProductFromConsole() throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
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
        int rolNum = DB.update(sql, infos[0], infos[1], infos[2], infos[3]);
        return rolNum > 0;
    }

}
