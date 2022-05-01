package IOHomework;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DAOForXls {
    // 从EXCEL表读入数据并返回学生列表
    public static ArrayList<Student> getInfo(String filePath) throws BiffException, IOException {
        ArrayList<Student> studentList = new ArrayList<>();
        //工作簿
        Workbook workbook = Workbook.getWorkbook(new File(filePath));
        //工作表
        Sheet sheet = workbook.getSheet(0);
        int rows = sheet.getRows();
        for (int i = 1; i < rows; i++) {
            Student student = new Student(sheet.getCell(0, i).getContents(), sheet.getCell(1, i).getContents(),
                    sheet.getCell(2, i).getContents(), sheet.getCell(3, i).getContents(),
                    sheet.getCell(4, i).getContents());
            studentList.add(student);
        }
        workbook.close();
        return studentList;
    }

    /*
      将学生成绩输出到 score.xls 文件中，格式为“学号，姓名，性别，
      总分，平均分”，,输出成功后显示提示信息“成功写入 excel”，并返回菜单
     */
    public static void outputToEXCEL(ArrayList<Student> studentList, String fileName) throws IOException, WriteException {
        WritableWorkbook book = Workbook.createWorkbook(new File(fileName));
        WritableSheet sheet = book.createSheet("Sheet1", 0);
        String[] titles = {"学号", "姓名", "性别", "总分", "平均分"};
        for (int i = 0; i < titles.length; i++) {
            Label label = new Label(i, 0, titles[i]);
            sheet.addCell(label);
        }
        ArrayList<ScoreInfo> infos = Handle.changeInToOut(studentList);
        int row = 1;
        for (ScoreInfo info : infos) {
            Label label = new Label(0, row, info.getId());
            sheet.addCell(label);
            label = new Label(1, row, info.getName());
            sheet.addCell(label);
            label = new Label(2, row, info.getGender());
            sheet.addCell(label);
            label = new Label(3, row, info.getScoreSum());
            sheet.addCell(label);
            label = new Label(4, row, info.getScoreAvg());
            sheet.addCell(label);
            row++;
        }
        book.write();   //对sheet的内容进行写入
        book.close();
    }

}
