package SQLHomework.Tool;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
加密类
*/
public class Encryption {

    //加密
    public static String getMD5String(String string) throws NoSuchAlgorithmException {
        //生成一个MD5加密计算摘要
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        //计算MD5函数
        md5.update(string.getBytes(StandardCharsets.UTF_8));
        //返回MD5的摘要信息
        byte[] b = md5.digest();
        int i;
        StringBuilder buf = new StringBuilder();
        for (byte value : b) {
            i = value;
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        return buf.toString();  //返回128bit的摘要值(32位16进制数)
    }
}
