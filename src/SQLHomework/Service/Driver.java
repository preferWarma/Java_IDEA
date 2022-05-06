package SQLHomework.Service;

import SQLHomework.DAO.DAOForProduct;
import SQLHomework.DAO.DAOForTxt;
import SQLHomework.DAO.DAOForUser;
import SQLHomework.DAO.DAOForXls;
import SQLHomework.DataBase.DB;
import SQLHomework.VO.Product;
import SQLHomework.VO.User;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String xlsFatherPath = "C:\\Users\\2961884371\\Desktop\\Program Text\\Java\\Java_IDEA\\SQLHomeworkData\\xls";
    private static final String txtFatherPath = "C:\\Users\\2961884371\\Desktop\\Program Text\\Java\\Java_IDEA\\SQLHomeworkData\\txt";
    private static final String xlsPath = "C:\\Users\\2961884371\\Desktop\\Program Text\\Java\\Java_IDEA\\SQLHomeworkData\\xls\\product.xls";
    private static final String txtPath = "C:\\Users\\2961884371\\Desktop\\Program Text\\Java\\Java_IDEA\\SQLHomeworkData\\txt\\product.txt";

    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException, ParseException, WriteException, IOException, BiffException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        //当前用户
        User user = Base.logOn();
        if (user == null) {
            System.exit(0);
        }
        while (true) {
            int choice = Base.mainMenu(user);
            switch (choice) {
                case 1: //收银
                    DAOForUser.payment(user);
                    break;
                case 2: //查询统计
                    DAOForUser.statisticsQuery();
                    break;
                case 3: //商品维护
                    if (!user.getRole().equals("管理员")) {
                        System.out.println("当前用户没有执行该项功能的权限");
                    } else {
                        boolean flag1 = true;
                        while (flag1) {
                            int choice1 = Base.sonMenu1();
                            switch (choice1) {
                                case 1: //从excel中导入数据
                                    ArrayList<Product> arrayList1 = DAOForXls.inputHelp(xlsPath);
                                    int num1 = DAOForProduct.input(arrayList1);
                                    System.out.println("成功从excel文件中导入" + num1 + "条商品数据");
                                    break;
                                case 2: //从文本文件中导入数据
                                    ArrayList<Product> arrayList2 = DAOForTxt.inputHelp(txtPath);
                                    int num2 = DAOForProduct.input(arrayList2);
                                    System.out.println("成功从文本文件中导入" + num2 + "条商品数据");
                                    break;
                                case 3: //键盘输入
                                    if (DAOForProduct.addProductFromConsole())
                                        System.out.println("添加成功");
                                    else
                                        System.out.println("添加失败");
                                    break;
                                case 4: //商品查询(模糊查询)
                                    DAOForProduct.fuzzyQuery();
                                    break;
                                case 5: //返回主菜单
                                    flag1 = false;
                                    break;
                            }
                        }
                    }
                    break;
                case 4: //修改密码
                    DAOForUser.changePassword(user);
                    break;
                case 5: //数据导出
                    boolean flag2 = true;
                    while (flag2) {
                        int choice2 = Base.sonMenu2();
                        switch (choice2) {
                            case 1: //导出到excel文件
                                int num1 = DAOForXls.output(xlsFatherPath);
                                if (num1 > 0)
                                    System.out.println("成功导出" + num1 + "条销售数据到excel文件");
                                else
                                    System.out.println("导出失败!");
                                break;
                            case 2: //导出到文本文件
                                int num2 = DAOForTxt.output(txtFatherPath);
                                if (num2 > 0)
                                    System.out.println("成功导出" + num2 + "条销售数据到文本文件");
                                else
                                    System.out.println("导出失败!");
                                break;
                            case 3: //返回主菜单
                                flag2 = false;
                                break;
                        }
                    }
                    break;
                case 6: //退出
                    System.out.println("确认退出系统吗?(Y/N)");
                    String confirm = scanner.next();
                    if (confirm.equals("Y") || confirm.equals("y")) {
                        System.out.println("欢迎下次继续使用");
                        DB.closeConnection(); //关闭数据库连接
                        System.exit(0);
                    } else
                        break;
            }
        }
    }
}
