package com.jlspider.fund.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jinlin
 * Date: 16-11-10
 * Time: 下午4:38
 * To change this template use File | Settings | File Templates.
 */
public class FundCompany {
    private String code;
    private String name;
    private List<Fund> fundList;
    private String fundPageUrl;   //基金列表页URL
    private Integer fundTotalPage;   //基金总页数


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

    public List<Fund> getFundList() {
        return fundList;
    }

    public void setFundList(List<Fund> fundList) {
        this.fundList = fundList;
    }

    public String getFundPageUrl() {
        return fundPageUrl;
    }

    public void setFundPageUrl(String fundPageUrl) {
        this.fundPageUrl = fundPageUrl;
    }

    public Integer getFundTotalPage() {
        return fundTotalPage;
    }

    public void setFundTotalPage(Integer fundTotalPage) {
        this.fundTotalPage = fundTotalPage;
    }
}
