package com.jlspider.fund.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jinlin
 * Date: 16-11-10
 * Time: 下午4:41
 * To change this template use File | Settings | File Templates.
 */
public class Fund {
    private String code;
    private String name;
    private String type;
    private Float curNetValue;  //单位净值
    private Float curAccumulativeTotal; //累计净值
    private List<FundNet> fundNetList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getCurNetValue() {
        return curNetValue;
    }

    public void setCurNetValue(Float curNetValue) {
        this.curNetValue = curNetValue;
    }

    public Float getCurAccumulativeTotal() {
        return curAccumulativeTotal;
    }

    public void setCurAccumulativeTotal(Float curAccumulativeTotal) {
        this.curAccumulativeTotal = curAccumulativeTotal;
    }

    public List<FundNet> getFundNetList() {
        return fundNetList;
    }

    public void setFundNetList(List<FundNet> fundNetList) {
        this.fundNetList = fundNetList;
    }
}
