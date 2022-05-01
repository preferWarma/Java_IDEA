package IOHomework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DAOForTxt {
    //从txt文件读取学生数据
    public static ArrayList<Student> getInfo(String filePath) throws FileNotFoundException {
        ArrayList<Student> studentList = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filePath));
        while (scanner.hasNextLine()) {
            String[] info = scanner.nextLine().split(" ");
            studentList.add(new Student(info[0], info[1], info[2], info[3], info[4]));
        }
        scanner.close();
        return studentList;
    }

    //将学生成绩处理后的信息写入到txt文件中
    public static void outputToTxt(ArrayList<Student> studentList, String filePath) throws IOException {
        StringBuilder line = new StringBuilder();
        ArrayList<ScoreInfo> infos = Handle.changeInToOut(studentList);
        for (ScoreInfo info : infos) {
            line.append(info.getId()).append(" ");
            line.append(info.getName()).append(" ");
            line.append((info.getGender())).append(" ");
            line.append((info.getScoreSum())).append(" ");
            line.append(info.getScoreAvg()).append("\n");
        }
        PrintWriter writer = new PrintWriter(filePath);
        writer.write(line.toString());
        writer.close();
    }
}
