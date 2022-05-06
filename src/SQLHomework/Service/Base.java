package SQLHomework.Service;

import SQLHomework.DAO.DAOForUser;
import SQLHomework.Tool.Check;
import SQLHomework.VO.User;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;

/*
登录和界面展示等基础功能
 */
public class Base {
    private static final Scanner scanner = new Scanner(System.in);

    //登录
    public static User logOn() throws SQLException, NoSuchAlgorithmException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        System.out.println("欢迎使用小刘超市收银系统, 请登陆");
        int chance = 3;
        String userName;
        String password;
        while (true) {
            System.out.println("请输入用户名: ");
            userName = scanner.nextLine();
            System.out.println("请输入密码: ");
            password = scanner.nextLine();
            if (Check.userCorrect(userName, password)) {
                break;
            } else {
                System.out.println("用户名或密码不正确");
                chance--;
                if (chance == 0) {
                    System.out.println("三次输入不正确，程序退出!");
                    return null;
                }
                System.out.println("你还有" + chance + "次输入机会, 请重新输入: ");
            }
        }
        return DAOForUser.getUser(userName, password);
    }

    //主菜单
    public static int mainMenu(User user) {
        System.out.println("\n===小刘超市收银系统===");
        System.out.println("1.收银");
        System.out.println("2.查询统计");
        System.out.println("3.商品维护");
        System.out.println("4.修改密码");
        System.out.println("5.数据导出");
        System.out.println("6.退出");
        System.out.println("当前收银员： " + user.getChrName());
        System.out.println("请选择(1——6)");
        int choice = Integer.parseInt(scanner.nextLine());
        while (choice > 6 || choice < 1) {
            System.out.println("输入无效, 请输入1——6的数");
            choice = scanner.nextInt();
        }
        return choice;
    }

    //商品维护子菜单(仅管理员可用)
    public static int sonMenu1() {
        System.out.println("\n===小刘超市商品管理维护===");
        System.out.println("1.从excel导入数据");
        System.out.println("2.从文本文件导入数据");
        System.out.println("3.键盘输入");
        System.out.println("4.商品查询");
        System.out.println("5.返回主菜单");
        System.out.println("请选择(1——5)");
        int choice = Integer.parseInt(scanner.nextLine());
        while (choice > 5 || choice < 1) {
            System.out.println("输入无效, 请输入1——5的数");
            choice = scanner.nextInt();
        }
        return choice;
    }

    //数据导出的子菜单
    public static int sonMenu2() {
        System.out.println("\n===小刘超市销售信息导出===");
        System.out.println("1.导出到excel文件");
        System.out.println("2.导出到文本文件");
        System.out.println("3.返回主菜单");
        System.out.println("请选择(1——3)");
        int choice = Integer.parseInt(scanner.nextLine());
        while (choice > 3 || choice < 1) {
            System.out.println("输入无效, 请输入1——3的数");
            choice = scanner.nextInt();
        }
        return choice;
    }

}
