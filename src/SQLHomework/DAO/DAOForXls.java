package SQLHomework.DAO;

import SQLHomework.VO.Product;
import SQLHomework.VO.SaleDetail;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DAOForXls {

    //产品数据导入
    public static ArrayList<Product> inputHelp(String filePath) throws IOException, BiffException {
        ArrayList<Product> arrayList = new ArrayList<>();
        Workbook workbook = Workbook.getWorkbook(new File(filePath));
        Sheet sheet = workbook.getSheet(0);
        int rows = sheet.getRows();
        for (int i = 1; i < rows; i++) {
            Product product = new Product(sheet.getCell(0, i).getContents(), sheet.getCell(1, i).getContents(),
                    BigDecimal.valueOf(Double.parseDouble(sheet.getCell(2, i).getContents())), sheet.getCell(3, i).getContents());
            arrayList.add(product);
        }
        workbook.close();
        return arrayList;
    }

    public static void main(String[] args) throws SQLException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        ArrayList<SaleDetail> arrayList = DAOForSaleDetail.getInfo();
        for (SaleDetail saleDetail : arrayList)
            System.out.println(saleDetail);
    }

    //销售信息数据导出xls
    public static int output(String fatherPath) throws IOException, SQLException, WriteException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        ArrayList<SaleDetail> arrayList = DAOForSaleDetail.getInfo();
        if (arrayList.size() < 1)
            return 0;
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String dateStr = simpleDateFormat.format(date);
        dateStr = dateStr.replace("-", "");
        WritableWorkbook workbook = Workbook.createWorkbook(new File(fatherPath + "\\saleDetail" + dateStr + ".xls"));
        WritableSheet sheet = workbook.createSheet("Sheet1", 0);
        String[] titles = {"流水号", "商品名称", "单价", "数量", "金额", "时间", "收银员"};
        for (int i = 0; i < titles.length; i++) {
            Label label = new Label(i, 0, titles[i]);
            sheet.addCell(label);
        }
        int row = 1;
        for (SaleDetail saleDetail : arrayList) {
            Label label = new Label(0, row, saleDetail.getLsh());
            sheet.addCell(label);
            label = new Label(1, row, saleDetail.getBarCode());
            sheet.addCell(label);
            label = new Label(2, row, saleDetail.getProductName());
            sheet.addCell(label);
            label = new Label(3, row, String.valueOf(saleDetail.getCount()));
            sheet.addCell(label);
            label = new Label(4, row, String.valueOf(saleDetail.getPrice()));
            sheet.addCell(label);
            label = new Label(5, row, saleDetail.getOperator());
            sheet.addCell(label);
            label = new Label(6, row, saleDetail.getSaleTime());
            sheet.addCell(label);
            row++;
        }
        workbook.write();
        workbook.close();
        return arrayList.size();
    }
}
