package IOHomework;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DAOForJson {
    //读取文本信息转化为字符串
    public static String fileToString(String filePath) throws FileNotFoundException {
        StringBuilder ans = new StringBuilder();
        Scanner scanner = new Scanner(new File(filePath));
        while (scanner.hasNextLine()) {
            ans.append(scanner.nextLine()).append("\n");
        }
        scanner.close();
        return ans.toString();
    }

    //将字符串写入文本中
    public static void writeString(String string, String filePath) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(filePath);
        writer.write(string);
        writer.close();
    }

    //借助Java类，生成对应的Java对象来解析数据
    public static ArrayList<Student> analysis(String jsonString) {
        Gson gson = new Gson();
        inEntry entry = gson.fromJson(jsonString, inEntry.class);
        return new ArrayList<>(Arrays.asList(entry.students));
    }

    //从json文件中读取学生信息获得学生链表
    public static ArrayList<Student> getInfo(String filePath) throws FileNotFoundException {
        String jsonString = fileToString(filePath);
        return analysis(jsonString);
    }

    //将学生成绩信息处理后写入json格式的文件
    public static void outputToJson(ArrayList<Student> studentList, String filePath) throws FileNotFoundException {
        outEntry entry = new outEntry();
        entry.setName("students");
        ArrayList<ScoreInfo> infos = Handle.changeInToOut(studentList);
        entry.setInfos(infos.toArray(new ScoreInfo[0]));

        Gson gson = new Gson();
        String jsonString = gson.toJson(entry);
        writeString(jsonString, filePath);
    }

    //输入实体
    static class inEntry {
        private String name;
        private Student[] students;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Student[] getStudents() {
            return students;
        }

        public void setStudents(Student[] students) {
            this.students = students;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "name='" + name + '\'' +
                    ", students=" + Arrays.toString(students) +
                    '}';
        }
    }

    //输出实体
    static class outEntry {
        private String name;
        private ScoreInfo[] infos;

        public outEntry() {
        }

        public outEntry(String name, ScoreInfo[] infos) {
            this.name = name;
            this.infos = infos;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ScoreInfo[] getInfos() {
            return infos;
        }

        public void setInfos(ScoreInfo[] infos) {
            this.infos = infos;
        }

    }
}
