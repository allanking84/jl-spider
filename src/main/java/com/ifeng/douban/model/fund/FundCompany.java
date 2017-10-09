package com.ifeng.douban.model.fund;

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
}
