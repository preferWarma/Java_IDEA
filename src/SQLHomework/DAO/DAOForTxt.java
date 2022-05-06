package SQLHomework.DAO;

import SQLHomework.VO.Product;
import SQLHomework.VO.SaleDetail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class DAOForTxt {

    //产品数据导入(txt)
    public static ArrayList<Product> inputHelp(String filePath) throws FileNotFoundException {
        ArrayList<Product> arrayList = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filePath));
        scanner.nextLine(); //第一行是数据说明
        while (scanner.hasNextLine()) {
            String[] infos = scanner.nextLine().split(" ");
            arrayList.add(new Product(infos[0], infos[1], BigDecimal.valueOf(Double.parseDouble(infos[2])), infos[3]));
        }
        scanner.close();
        return arrayList;
    }

    //销售信息数据导出txt
    public static int output(String fatherPath) throws FileNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        ArrayList<SaleDetail> arrayList = DAOForSaleDetail.getInfo();
        if (arrayList.size() < 1)
            return 0;
        StringBuilder ans = new StringBuilder("所有销售数据如下: \n");
        ans.append("流水号\t商品名称\t单价\t数量\t金额\t时间\t收银员\n");
        for (SaleDetail saleDetail : arrayList) {
            ans.append(saleDetail.getLsh()).append(" ");
            ans.append(saleDetail.getBarCode()).append(" ");
            ans.append(saleDetail.getProductName()).append(" ");
            ans.append(saleDetail.getPrice()).append(" ");
            ans.append(saleDetail.getCount()).append(" ");
            ans.append(saleDetail.getOperator()).append(" ");
            ans.append(saleDetail.getSaleTime()).append("\n");
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String dateStr = simpleDateFormat.format(date);
        PrintWriter writer = new PrintWriter(fatherPath + "\\saleDetail" + dateStr + ".txt");
        writer.write(ans.toString());
        writer.close();
        return arrayList.size();
    }

}
