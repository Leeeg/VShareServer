package com.elon.item.entity;

import java.io.Serializable;

/**
 * @author 徐贵平
 * @time 2018年05月10日
 * @since JDK1.7
 **/
public class Item implements Serializable{

    private Integer itemId;

    private Integer itemType;

    private Integer stock;

    private String itemName;

    private String itemImg;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImg() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }
}
