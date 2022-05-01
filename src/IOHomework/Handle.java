package IOHomework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeMap;

public class Handle {   //处理一些其他功能
    static Scanner scanner = new Scanner(System.in);

    //菜单
    public static int menu() {
        System.out.println("=====学生成绩管理系统======");
        System.out.println("1.从excel中加载数据");
        System.out.println("2.从文本文件中加载数据");
        System.out.println("3.从xml文件中加载数据");
        System.out.println("4.从json文件中加载数据");
        System.out.println("5.键盘输入数据");
        System.out.println("6.成绩查询");
        System.out.println("7.输出到excel文件");
        System.out.println("8.输出到纯文本文件");
        System.out.println("9.输出到xml文件");
        System.out.println("10.输出到json文件");
        System.out.println("11.修改密码");
        System.out.println("12.退出");
        System.out.println("请输入选项: ");
        String choice = scanner.next();
        while (Integer.parseInt(choice) > 12 || Integer.parseInt(choice) < 1) {
            System.out.println("输入错误, 请输入1——12的数");
            choice = scanner.next();
        }
        return Integer.parseInt(choice);
    }

    //登录
    public static boolean logOn(String propertyPath) throws IOException {
        int chance = 3;
        while (true) {
            System.out.println("请输入用户名: ");
            String userName = scanner.nextLine();
            System.out.println("请输入密码: ");
            String password = scanner.nextLine();
            if (userName.equals(DAOForProperty.getUserName(propertyPath))
                    && password.equals(DAOForProperty.getPassword(propertyPath))) {
                break;
            } else {
                System.out.println("用户名或密码不正确");
                chance--;
                if (chance == 0) {
                    System.out.println("三次输入不正确，程序退出!");
                    return false;
                }
                System.out.println("你还有" + chance + "次输入机会, 请重新输入: ");
            }
        }
        return true;
    }

    //查询成绩
    public static void findScore(ArrayList<Student> studentList) {
        scanner.nextLine();
        System.out.println("请输入待查询的学生的学号: ");
        String id = scanner.nextLine();
        boolean flag = false;
        for (Student student : studentList) {
            if (id.equals(student.getId())) {
                if (!flag) {
                    System.out.println("学号  姓名  性别\t课程名称  \t成绩");
                }
                flag = true;
                System.out.print(student.getId() + " ");
                System.out.print(student.getName() + "  ");
                System.out.print(student.getGender() + "\t");
                System.out.print(student.getCourseName() + "\t\t");
                System.out.println(student.getScore());
            }
        }
        if (!flag) {
            System.out.println("不存在该学生");
        }
    }

    //修改密码
    public static void changePassword(String propertyPath) throws IOException {
        scanner.nextLine();
        System.out.println("请输入原密码: ");
        String oldPassword = scanner.nextLine();
        if (oldPassword.equals(DAOForProperty.getPassword(propertyPath))) {
            System.out.println("请输入新密码: ");
            DAOForProperty.setPassword(scanner.nextLine(), propertyPath);
            System.out.println("密码已修改!");
        } else {
            System.out.println("密码输入错误!");
        }
    }

    //获取学生数量
    public static int getStudentSize(ArrayList<Student> studentList) {
        HashSet<String> studentHashSet = new HashSet<>();
        for (Student student : studentList) {
            studentHashSet.add(student.getId());
        }
        return studentHashSet.size();
    }

    //通过输入信息获得输出信息
    public static ArrayList<ScoreInfo> changeInToOut(ArrayList<Student> studentList) {
        ArrayList<ScoreInfo> infos = new ArrayList<>();
        TreeMap<String, ArrayList<Student>> treeMap = new TreeMap<>();
        for (Student student : studentList) {
            String str = student.getId();
            if (treeMap.containsKey(str)) {
                treeMap.get(str).add(student);
            } else {
                treeMap.put(str, new ArrayList<>());
                treeMap.get(str).add(student);
            }

        }
        for (String string : treeMap.keySet()) {
            double sumScore = 0d;
            double avgScore;
            for (Student student : treeMap.get(string)) {
                sumScore += Double.parseDouble(student.getScore());
            }
            avgScore = sumScore / treeMap.get(string).size();
            Student student = treeMap.get(string).get(0);
            infos.add(new ScoreInfo(student.getId(), student.getName(), student.getGender(), Double.toString(sumScore), Double.toString(avgScore)));
        }
        return infos;
    }

    //从键盘输入信息
    public static void getInfo(ArrayList<Student> studentList) {
        scanner.nextLine();
        System.out.println("学号, 姓名, 性别, 课程名称, 成绩");
        String line = scanner.nextLine();
        String[] strings = line.split("[,，]");
        Student student = new Student(strings[0], strings[1], strings[2], strings[3], strings[4]);
        if (isExistence(student, studentList)) {    //如果已经存在
            System.out.println("该学生不能重复录用信息, 请重新输入");
        } else {
            studentList.add(student);
            System.out.println("添加成功");
        }
    }

    //检验该成绩信息是否已经录用
    public static boolean isExistence(Student student, ArrayList<Student> students) {
        for (Student stu : students) {
            if (stu.getId().equals(student.getId()) && stu.getCourseName().equals(student.getCourseName())) {
                return true;
            }
        }
        return false;
    }

}
