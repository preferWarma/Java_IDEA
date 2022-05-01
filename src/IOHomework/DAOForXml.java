package IOHomework;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DAOForXml {
    //从xml文件读取学生信息保存在学生链表中
    public static ArrayList<Student> getInfo(String filePath) throws DocumentException {
        ArrayList<Student> studentList = new ArrayList<>();
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(filePath));
        Element root = document.getRootElement();   //获取根节点
        List<Element> list = root.elements("student");
        for (Element element : list) {  //遍历root下的所有student子节点
            Element eleId = element.element("id");
            String id = eleId.getText();
            Element eleName = element.element("name");
            String name = eleName.getText();
            Element eleGender = element.element("gender");
            String gender = eleGender.getText();
            Element eleCourseName = element.element("courseName");
            String courseName = eleCourseName.getText();
            Element eleScore = element.element("score");
            String score = eleScore.getText();
            studentList.add(new Student(id, name, gender, courseName, score));
        }
        return studentList;
    }

    //将学生信息写入到xml文件中
    public static void outputToXML(ArrayList<Student> studentList, String filePath) throws IOException {
        Document document = DocumentHelper.createDocument();    //创建文档
        Element root = document.addElement("students");    //创建根节点
        ArrayList<ScoreInfo> infos = Handle.changeInToOut(studentList);
        for (ScoreInfo info : infos) {
            Element scoreInfo = root.addElement("student");
            Element id = scoreInfo.addElement("id");
            Element name = scoreInfo.addElement("name");
            Element gender = scoreInfo.addElement("gender");
            Element scoreSum = scoreInfo.addElement("scoreSum");
            Element scoreAvg = scoreInfo.addElement("scoreAvg");
            id.setText(info.getId());
            name.setText(info.getName());
            gender.setText(info.getGender());
            scoreSum.setText(info.getScoreSum());
            scoreAvg.setText(info.getScoreAvg());
        }
        //控制编码
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
        XMLWriter writer = new XMLWriter(new FileWriter(filePath), format);
        writer.write(document);
        writer.close();
    }
}
