package IOHomework;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    static Scanner scanner = new Scanner(System.in);
    static String propertyPath = "C:\\Users\\2961884371\\Desktop\\Program Text\\Java\\Java_IDEA\\IOHomeworkData\\users.property";
    static String xlsPath = "C:\\Users\\2961884371\\Desktop\\Program Text\\Java\\Java_IDEA\\IOHomeworkData\\student.xls";
    static String xlsScorePath = "C:\\Users\\2961884371\\Desktop\\Program Text\\Java\\Java_IDEA\\IOHomeworkData\\score.xls";
    static String jsonPath = "C:\\Users\\2961884371\\Desktop\\Program Text\\Java\\Java_IDEA\\IOHomeworkData\\student.json";
    static String jsonScorePath = "C:\\Users\\2961884371\\Desktop\\Program Text\\Java\\Java_IDEA\\IOHomeworkData\\score.json";
    static String txtPath = "C:\\Users\\2961884371\\Desktop\\Program Text\\Java\\Java_IDEA\\IOHomeworkData\\student.txt";
    static String txtScorePath = "C:\\Users\\2961884371\\Desktop\\Program Text\\Java\\Java_IDEA\\IOHomeworkData\\score.txt";
    static String xmlPath = "C:\\Users\\2961884371\\Desktop\\Program Text\\Java\\Java_IDEA\\IOHomeworkData\\student.xml";
    static String xmlScorePath = "C:\\Users\\2961884371\\Desktop\\Program Text\\Java\\Java_IDEA\\IOHomeworkData\\score.xml";
    static ArrayList<Student> students = new ArrayList<>();
    static int studentSize = 0;

    public static void main(String[] args) throws IOException, BiffException, WriteException, DocumentException {
        boolean log = Handle.logOn(propertyPath);
        if (!log) {
            System.exit(0);
        }
        while (true) {
            int choice = Handle.menu();
            switch (choice) {
                case 1: //读取student.xls文件(完成)
                    students = DAOForXls.getInfo(xlsPath);
                    studentSize = Handle.getStudentSize(students);
                    if (studentSize != 0) {
                        System.out.println("成功导入" + studentSize + "个学生");
                    }
                    break;
                case 2: //读取student.txt文件(完成)
                    students = DAOForTxt.getInfo(txtPath);
                    studentSize = Handle.getStudentSize(students);
                    if (studentSize != 0) {
                        System.out.println("成功导入" + studentSize + "个学生");
                    }
                    break;
                case 3: //读取student.xml文件(完成)
                    students = DAOForXml.getInfo(xmlPath);
                    studentSize = Handle.getStudentSize(students);
                    if (studentSize != 0) {
                        System.out.println("成功导入" + studentSize + "个学生");
                    }
                    break;
                case 4: //读取student.json文件(完成)
                    students = DAOForJson.getInfo(jsonPath);
                    studentSize = Handle.getStudentSize(students);
                    if (studentSize != 0) {
                        System.out.println("成功导入" + studentSize + "个学生");
                    }
                    break;
                case 5: //从键盘录入学生信息
                    Handle.getInfo(students);
                    break;
                case 6: //查询成绩(完成)
                    Handle.findScore(students);
                    break;
                case 7: //写入到score.xls文件(完成)
                    DAOForXls.outputToEXCEL(students, xlsScorePath);
                    System.out.println("成功写入excel");
                    break;
                case 8: //写入到score.txt文件(完成)
                    DAOForTxt.outputToTxt(students, txtScorePath);
                    System.out.println("成功写入txt");
                    break;
                case 9: //写入到score.xml文件(完成)
                    DAOForXml.outputToXML(students, xmlScorePath);
                    System.out.println("成功写入xml");
                    break;
                case 10: //写入到score.json文件(完成)
                    DAOForJson.outputToJson(students, jsonScorePath);
                    System.out.println("成功写入json");
                    break;
                case 11: //修改密码(完成)
                    Handle.changePassword(propertyPath);
                    break;
                case 12: //退出系统(完成)
                    System.out.println("确认退出系统吗(Y/N)?");
                    String confirm = scanner.next();
                    if (confirm.equals("Y") || confirm.equals("y"))
                        System.exit(0);
                    else
                        break;
            }
        }
    }
}
