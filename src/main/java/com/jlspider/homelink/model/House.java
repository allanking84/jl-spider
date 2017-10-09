package com.jlspider.homelink.model;

/**
 * Created with IntelliJ IDEA.
 * User: jinlin
 * Date: 16-11-11
 * Time: 下午2:49
 * To change this template use File | Settings | File Templates.
 */
public class House {
    private Integer unitPrice;     //单价
    private String houseInfo;            //描述
    private Integer totalPrice;     //总价
    private String title;           //

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getHouseInfo() {
        return houseInfo;
    }

    public void setHouseInfo(String houseInfo) {
        this.houseInfo = houseInfo;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

