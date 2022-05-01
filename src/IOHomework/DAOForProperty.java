package IOHomework;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class DAOForProperty {
    public static void main(String[] args) throws IOException {
        String filePath = "C:\\Users\\2961884371\\Desktop\\Program Text\\Java\\Java_IDEA\\IOHomeworkData\\users.property";
        createProperty(filePath);
        System.out.println(getUserName(filePath));
        System.out.println(getPassword(filePath));
        setPassword("123456", filePath);
        System.out.println(getPassword(filePath));
    }

    //修改密码
    public static void setPassword(String newPassword, String filePath) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
        properties.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        inputStream.close();
        properties.setProperty("password", newPassword);
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        properties.store(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8), "");
        fileOutputStream.close();
    }

    //生成Property文件
    public static void createProperty(String filePath) throws IOException {
        Properties properties = new Properties();
        properties.setProperty("userName", "管理员");
        properties.setProperty("password", "123456");
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        properties.store(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8), "");
        fileOutputStream.close();
    }

    //获得的密码
    public static String getPassword(String filePath) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
        properties.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        inputStream.close();
        return properties.getProperty("password");
    }

    //获得用户名
    public static String getUserName(String filePath) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
        properties.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        inputStream.close();
        return properties.getProperty("userName");
    }
}
