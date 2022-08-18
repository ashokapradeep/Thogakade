package lk.ijse.pos.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Item {
    @Id
    private String code;
    private String description;
    private Double unitPrice;
    private Integer qtyAvailable;

    public Item(String code, String description, Double unitPrice, Integer qtyAvailable) {
        this.code = code;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyAvailable = qtyAvailable;
    }

    public Item() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQtyAvailable() {
        return qtyAvailable;
    }

    public void setQtyAvailable(Integer qtyAvailable) {
        this.qtyAvailable = qtyAvailable;
    }
    @Override
    public String toString() {
        return "ItemDto{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", qtyAvailable=" + qtyAvailable +
                '}';
    }
}
