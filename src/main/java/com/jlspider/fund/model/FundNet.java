package com.jlspider.fund.model;

/**
 * Created with IntelliJ IDEA.
 * User: jinlin
 * Date: 16-11-10
 * Time: 下午4:48
 * To change this template use File | Settings | File Templates.
 */
public class FundNet {
    private String date;
    private Float netValue;  //单位净值
    private Float accumulativeTotal; //累计净值

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getNetValue() {
        return netValue;
    }

    public void setNetValue(Float netValue) {
        this.netValue = netValue;
    }

    public Float getAccumulativeTotal() {
        return accumulativeTotal;
    }

    public void setAccumulativeTotal(Float accumulativeTotal) {
        this.accumulativeTotal = accumulativeTotal;
    }
}
