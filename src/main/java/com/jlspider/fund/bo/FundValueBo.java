package com.jlspider.fund.bo;


import com.jlspider.fund.model.Fund;
import com.jlspider.fund.model.FundCompany;
import com.jlspider.fund.model.FundNet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.net.SocketTimeoutException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jinlin
 * Date: 16-11-11
 * Time: 下午2:51
 * To change this template use File | Settings | File Templates.
 */
public interface FundValueBo {
    public List<Fund> getAllFundsByCompany(String fundCompanyUrl,Integer totalPage);
    public List<Fund> getAllFundsByCompany(String fundCompanyUrl);
    public List<FundNet> getNetByCode(String code) throws SocketTimeoutException;
    public void execute();
    public HSSFWorkbook exportToExcel(List<FundCompany> companies);
}
