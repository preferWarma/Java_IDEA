package SQLHomework.VO;

import java.math.BigDecimal;

public class Product {
    private String barCode;
    private String productName;
    private BigDecimal price;
    private String supply;

    public Product(String barCode, String productName, BigDecimal price, String supply) {
        this.barCode = barCode;
        this.productName = productName;
        this.price = price;
        this.supply = supply;
    }

    public Product() {
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSupply() {
        return supply;
    }

    public void setSupply(String supply) {
        this.supply = supply;
    }
}
