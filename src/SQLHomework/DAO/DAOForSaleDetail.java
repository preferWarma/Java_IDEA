package SQLHomework.DAO;

import SQLHomework.DataBase.DB;
import SQLHomework.VO.SaleDetail;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DAOForSaleDetail {

    //获取数据库中的销售信息
    public static @NotNull ArrayList<SaleDetail> getInfo() throws SQLException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        String sql = "select * from homework_java.saledetail";
        return DB.query(sql, SaleDetail.class);
    }

    //流水号自增
    public static @NotNull String createNumber() throws SQLException, NoSuchFieldException, IllegalAccessException, InstantiationException {    //产生流水号
        int count = getCount();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(date) + String.format("%04d", count + 1);
    }

    //获取当日流水单数
    public static int getCount() throws SQLException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = simpleDateFormat.format(new Date());
        String sql = "select * from homework_java.saledetail where saleTime like ?";
        ArrayList<SaleDetail> arrayList = DB.query(sql, SaleDetail.class, dateStr + "%");
        return arrayList.size();
    }
}
