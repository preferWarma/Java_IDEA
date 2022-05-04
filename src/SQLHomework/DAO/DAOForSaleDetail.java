package SQLHomework.DAO;

import SQLHomework.DataBase.DB;
import SQLHomework.VO.SaleDetail;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOForSaleDetail {

    private static final Connection connection = DB.getConnection();

    //获取数据库中的销售信息
    public static ArrayList<SaleDetail> getInfo() throws SQLException {
        ArrayList<SaleDetail> arrayList = new ArrayList<>();
        String sql = "select * from homework_java.saledetail";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String lsh = resultSet.getString("lsh");
            String barCode = resultSet.getString("barCode");
            String productName = resultSet.getString("productName");
            BigDecimal price = resultSet.getBigDecimal("price");
            int count = resultSet.getInt("count");
            String saleTime = resultSet.getString("saleTime");
            String chrName = resultSet.getString("operator");
            arrayList.add(new SaleDetail(lsh,barCode,productName,price,count,saleTime,chrName));
        }
        return arrayList;
    }

}
