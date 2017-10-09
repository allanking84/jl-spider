package com.jlspider.fund.bo.impl;

import cn.wanghaomiao.xpath.model.JXDocument;
import cn.wanghaomiao.xpath.model.JXNode;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import com.google.gson.Gson;
import com.jlspider.fund.bo.FundValueBo;
import com.jlspider.fund.model.Fund;
import com.jlspider.fund.model.FundCompany;
import com.jlspider.fund.model.FundNet;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jinlin
 * Date: 16-11-11
 * Time: 下午2:54
 * To change this template use File | Settings | File Templates.
 */
public class FundValueBoImpl implements FundValueBo {
    private static Logger logger = LoggerFactory.getLogger(FundValueBoImpl.class);
    private Set<String> huaanTtCodeSet = new HashSet<String>(){{
        add("160420");
        add("001071");
        add("000217");
        add("000216");
        add("040005");
        add("040010");
        add("040009");
        add("040008");
        add("001028");
        add("040023");
        add("040022");
        add("001072");
        add("040019");
        add("000150");
        add("002534");
        add("040001");
        add("000149");
        add("000294");
        add("160415");
        add("040037");
        add("040036");
        add("040021");
        add("000549");
        add("040013");
        add("000376");
        add("000373");
        add("040012");
        add("040045");
        add("001905");
        add("002399");
        add("002398");
        add("000072");
        add("002364");
        add("002363");
        add("160419");
        add("001282");
        add("040004");
        add("040041");
        add("040040");
        add("040026");
        add("001311");
        add("000590");
        add("001800");
        add("040018");
        add("001139");
        add("001312");
        add("002144");
        add("000708");
        add("040002");
        add("160417");
        add("040007");
        add("040035");
        add("040020");
        add("040015");
        add("040190");
        add("160418");
        add("000313");
        add("000312");
        add("040016");
        add("040180");
        add("519909");
        add("519002");
        add("001445");
        add("040025");
        add("001694");
        add("040011");
        add("001104");
    }};

    private Set<String> huaxiaTtCodeSet = new HashSet<String>(){{
        add("001042");
        add("000001");
        add("000071");
        add("000021");
        add("000948");
        add("001924");
        add("001023");
        add("001021");
        add("000016");
        add("288001");
        add("000015");
        add("288102");
        add("001003");
        add("001001");
        add("000014");
        add("001013");
        add("000048");
        add("001011");
        add("000047");
        add("000061");
        add("519029");
        add("002604");
        add("160314");
        add("002251");
        add("002011");
        add("001033");
        add("002980");
        add("001031");
        add("000031");
        add("160311");
        add("002001");
        add("002021");
        add("000946");
        add("000945");
        add("000011");
        add("002264");
        add("001052");
        add("288002");
        add("002345");
        add("519918");
        add("000041");
        add("002230");
        add("002031");
        add("519908");
        add("002229");
        add("000051");
        add("001063");
        add("001061");
        add("000975");
        add("001016");
        add("001015");
        add("001928");
        add("001927");
        add("001051");
        add("501050");
        add("001045");
    }};

    private Set<String> huashangTtCodeSet = new HashSet<String>(){{
        add("630002");
        add("001723");
        add("001959");
        add("000800");
        add("000541");
        add("001457");
        add("630005");
        add("630010");
        add("630011");
        add("000654");
        add("630103");
        add("630003");
        add("001822");
        add("001143");
        add("001106");
        add("630008");
        add("002596");
        add("630107");
        add("630007");
        add("001933");
        add("630006");
        add("000481");
        add("000938");
        add("000390");
        add("001448");
        add("000463");
        add("000609");
        add("000937");
        add("001752");
        add("001751");
        add("002669");
        add("166301");
        add("630001");
        add("630109");
        add("630009");
        add("000279");
        add("630015");
        add("630016");
        add("001449");
    }};

    @Override
    public List<Fund> getAllFundsByCompany(String fundCompanyUrl, Integer totalPage) {
        List<Fund> funds = new ArrayList<Fund>();
//         fundCompanyUrl =  "http://market.fund123.cn/result/index/gs28-ft-sya-syb-syc-syd-sye-syf-syg-syh-nv-ljnv-sh-fc-fm-pjh-pjz-i-ic-o-p";
        for(int pageNo =1; pageNo <= totalPage; ){
            List<Fund> singlePageFunds = new ArrayList<Fund>();
            String url =  fundCompanyUrl+(pageNo);     //华安
            logger.info("pageNo:"+pageNo);
            JXDocument jxDocument = null;
            try {
                Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0").get();
                jxDocument = new JXDocument(doc);

                String xpath = "//*[@id='resutlTbody']//td[3]/a/text()";        //代码
                System.out.println("current xpath:" + xpath);
                List<JXNode> rs = jxDocument.selN(xpath);

                String namexpath = "//*[@id='resutlTbody']//td[4]/a/text()";        //名称
                System.out.println("current xpath:" + namexpath);
                List<JXNode> namers = jxDocument.selN(namexpath);

                String typexpath = "//*[@id='resutlTbody']//td[5]/text()";        //类型
                System.out.println("current xpath:" + typexpath);
                List<JXNode> typers = jxDocument.selN(typexpath);

                for (int idx = 0; idx < rs.size(); idx++) {
                    JXNode n = rs.get(idx);
                    if (!n.isText()) {
                        int index = n.getElement().siblingIndex();
                        System.out.println(index);
                    }
                    Fund newFund = new Fund();
                    newFund.setCode(n.toString());
                    newFund.setName(namers.get(idx).toString());
                    newFund.setType(typers.get(idx).toString());
                    logger.info(newFund.getCode()+"\t"+newFund.getName()+"\t"+newFund.getType());
                    singlePageFunds.add(idx,newFund);

                    //查找数米缺失的基金
                    if(fundCompanyUrl.startsWith("http://market.fund123.cn/result/index/gs28")){
                        huaanTtCodeSet.remove(newFund.getCode());
                    }

                    if(fundCompanyUrl.startsWith("http://market.fund123.cn/result/index/gs31")){
                        huaxiaTtCodeSet.remove(newFund.getCode());
                    }

                    if(fundCompanyUrl.startsWith("http://market.fund123.cn/result/index/gs20")){
                        huashangTtCodeSet.remove(newFund.getCode());
                    }
                }

                funds.addAll(singlePageFunds);

            } catch (Exception e) {
               logger.error("[获取基金列表失败][url][pageNo:{}]",url,pageNo,e);
            }
            pageNo ++;
        }

        if(fundCompanyUrl.startsWith("http://market.fund123.cn/result/index/gs28")){
            logger.info("华安缺失:{}",huaanTtCodeSet);
        }

        if(fundCompanyUrl.startsWith("http://market.fund123.cn/result/index/gs31")){
            logger.info("华夏缺失:{}",huaxiaTtCodeSet);
        }
        if(fundCompanyUrl.startsWith("http://market.fund123.cn/result/index/gs20")){
            logger.info("华商缺失:{}",huashangTtCodeSet);
        }

        List<Fund> missingFunds = getMissingFunds(fundCompanyUrl);
        if(!missingFunds.isEmpty()){
            funds.addAll(missingFunds);
        }

        logger.info("funds size:"+funds.size());

        return funds;
    }

    @Override
    public List<Fund> getAllFundsByCompany(String fundCompanyUrl){
        List<Fund> funds = new ArrayList<Fund>();
            JXDocument jxDocument = null;
            try {
                Document doc = Jsoup.connect(fundCompanyUrl).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0").get();
                jxDocument = new JXDocument(doc);

                String xpath = "//*[@id='tb_1_0']/tbody[2]//td[1]/text()";        //代码
                System.out.println("current xpath:" + xpath);
                List<JXNode> rs = jxDocument.selN(xpath);

                String namexpath = "//*[@id='tb_1_0']/tbody[2]//td[1]/a/text()";        //名称
                System.out.println("current xpath:" + namexpath);
                List<JXNode> namers = jxDocument.selN(namexpath);

                String typexpath = "//*[@id='tb_1_0']/tbody[2]//td[3]/text()";        //类型
                System.out.println("current xpath:" + typexpath);
                List<JXNode> typers = jxDocument.selN(typexpath);

                for (int idx = 0; idx < rs.size(); idx++) {
                    JXNode n = rs.get(idx);
                    if (!n.isText()) {
                        int index = n.getElement().siblingIndex();
                        System.out.println(index);
                    }
                    Fund newFund = new Fund();
                    newFund.setCode(n.toString());
                    newFund.setName(namers.get(idx).toString());
                    newFund.setType(typers.get(idx).toString());
                    logger.info(newFund.getCode()+"\t"+newFund.getName()+"\t"+newFund.getType());


                    //查找数米缺失的基金
                    if(fundCompanyUrl.startsWith("http://market.fund123.cn/result/index/gs28")){
                        huaanTtCodeSet.remove(newFund.getCode());
                    }

                    if(fundCompanyUrl.startsWith("http://market.fund123.cn/result/index/gs31")){
                        huaxiaTtCodeSet.remove(newFund.getCode());
                    }

                    if(fundCompanyUrl.startsWith("http://market.fund123.cn/result/index/gs20")){
                        huashangTtCodeSet.remove(newFund.getCode());
                    }
                    funds.add(newFund);
                }



            } catch (Exception e) {
                logger.error("[获取基金列表失败][url][pageNo:{}]",fundCompanyUrl,e);
            }


        if(fundCompanyUrl.startsWith("http://market.fund123.cn/result/index/gs28")){
            logger.info("华安缺失:{}",huaanTtCodeSet);
        }

        if(fundCompanyUrl.startsWith("http://market.fund123.cn/result/index/gs31")){
            logger.info("华夏缺失:{}",huaxiaTtCodeSet);
        }
        if(fundCompanyUrl.startsWith("http://market.fund123.cn/result/index/gs20")){
            logger.info("华商缺失:{}",huashangTtCodeSet);
        }

        List<Fund> missingFunds = getMissingFunds(fundCompanyUrl);
        if(!missingFunds.isEmpty()){
            funds.addAll(missingFunds);
        }

        logger.info("funds size:"+funds.size());

        return funds;
    }

    private List<Fund> getMissingFunds(String fundCompanyUrl){

        List<Fund> missingFunds = Lists.newArrayList();
        //华安
        if(fundCompanyUrl.startsWith("http://market.fund123.cn/result/index/gs28")){

            Fund newFund1 = new Fund();
            newFund1.setCode("160420");
            newFund1.setName("华安创业板50指数分级");
            newFund1.setType("指数型");
            missingFunds.add(newFund1);
            Fund newFund2 = new Fund();
            newFund2.setCode("001028");
            newFund2.setName("华安物联网主题股票");
            newFund2.setType("股票型");
            missingFunds.add(newFund2);
            Fund newFund3 = new Fund();
            newFund3.setCode("001072");
            newFund3.setName("华安智能装备主题股票");
            newFund3.setType("股票型");
            missingFunds.add(newFund3);
            Fund newFund4 = new Fund();
            newFund4.setCode("002144");
            newFund4.setName("华安新优选灵活配置混合C");
            newFund4.setType("混合型");
            missingFunds.add(newFund4);
            Fund newFund5 = new Fund();
            newFund5.setCode("000312");
            newFund5.setName("华安沪深300量化增强A");
            newFund5.setType("指数型");
            missingFunds.add(newFund5);
            Fund newFund6 = new Fund();
            newFund6.setCode("000313");
            newFund6.setName("华安沪深300量化增强C");
            newFund6.setType("指数型");
            missingFunds.add(newFund6);
            Fund newFund7 = new Fund();
            newFund7.setCode("160418");
            newFund7.setName("华安中证银行指数分级");
            newFund7.setType("指数型");
            missingFunds.add(newFund7);
            Fund newFund8 = new Fund();
            newFund8.setCode("160419");
            newFund8.setName("华安中证全指证券分级");
            newFund8.setType("指数型");
            missingFunds.add(newFund8);
            Fund newFund9 = new Fund();
            newFund9.setCode("002534");
            newFund9.setName("华安稳固收益债券A");
            newFund9.setType("债券型");
            missingFunds.add(newFund9);
            Fund newFund10 = new Fund();
            newFund10.setCode("000373");
            newFund10.setName("华安中证医药ETF联接A");
            newFund10.setType("指数型");
            missingFunds.add(newFund10);
            Fund newFund11 = new Fund();
            newFund11.setCode("001139");
            newFund11.setName("华安新动力灵活配置混合");
            newFund11.setType("混合型");
            missingFunds.add(newFund11);
            Fund newFund12 = new Fund();
            newFund12.setCode("000376");
            newFund12.setName("华安中证医药ETF联接C");
            newFund12.setType("指数型");
            missingFunds.add(newFund12);
            Fund newFund13 = new Fund();
            newFund13.setCode("001104");
            newFund13.setName("华安新丝路主题股票");
            newFund13.setType("股票型");
            missingFunds.add(newFund13);
        }

        //华夏
        if(fundCompanyUrl.startsWith("http://market.fund123.cn/result/index/gs31")){

            Fund newFund1 = new Fund();
            newFund1.setCode("001015");
            newFund1.setName("华夏沪深300指数增强A");
            newFund1.setType("指数型");
            missingFunds.add(newFund1);
            Fund newFund2 = new Fund();
            newFund2.setCode("001016");
            newFund2.setName("华夏沪深300指数增强C");
            newFund2.setType("指数型");
            missingFunds.add(newFund2);
            Fund newFund3 = new Fund();
            newFund3.setCode("002264");
            newFund3.setName("华夏乐享健康混合");
            newFund3.setType("混合型");
            missingFunds.add(newFund3);
            Fund newFund4 = new Fund();
            newFund4.setCode("001045");
            newFund4.setName("华夏可转债增强债券A");
            newFund4.setType("债券型");
            missingFunds.add(newFund4);
            Fund newFund5 = new Fund();
            newFund5.setCode("501050");
            newFund5.setName("华夏上证50AH优选指数");
            newFund5.setType("指数型");
            missingFunds.add(newFund5);
            Fund newFund6 = new Fund();
            newFund6.setCode("002980");
            newFund6.setName("华夏创新前沿股票");
            newFund6.setType("股票型");
            missingFunds.add(newFund6);
        }
        //华商
        if(fundCompanyUrl.startsWith("http://market.fund123.cn/result/index/gs20")){

            Fund newFund1 = new Fund();
            newFund1.setCode("001751");
            newFund1.setName("华商信用增强债券A");
            newFund1.setType("债券型");
            missingFunds.add(newFund1);
            Fund newFund2 = new Fund();
            newFund2.setCode("001752");
            newFund2.setName("华商信用增强债券C");
            newFund2.setType("债券型");
            missingFunds.add(newFund2);
        }
        return missingFunds;

    }

    @Override
    public List<FundNet> getNetByCode(String code) throws SocketTimeoutException{
        String netUrl = "http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&page=1&per=1214&sdate=&edate=&rt=0.16949451062828302&code="+code;
        logger.info("code:{}",code);
        List<FundNet> fundNetList = new ArrayList<FundNet>();

        JXDocument jxDocument = null;
        try {
            Document doc = Jsoup.connect(netUrl).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0").get();
            jxDocument = new JXDocument(doc);

            String datepath = "//html/body/table/tbody//td[1]/text()";        //日期
            System.out.println("current xpath:" + datepath);
            List<JXNode> daters = jxDocument.selN(datepath);

            String dwxpath = "//html/body/table/tbody//td[2]/text()";        //单位
            System.out.println("current xpath:" + dwxpath);
            List<JXNode> dwrs = jxDocument.selN(dwxpath);

            String ljxpath = "//html/body/table/tbody//td[3]/text()";        //累计
            System.out.println("current xpath:" + ljxpath);
            List<JXNode> ljrs = jxDocument.selN(ljxpath);

            for (int idx = 0; idx < ljrs.size(); idx++) {
                JXNode n = daters.get(idx);
                if (!n.isText()) {
                    int index = n.getElement().siblingIndex();
                    System.out.println(index);
                }
                FundNet newFund = new FundNet();
                newFund.setDate(daters.get(idx).toString());
                newFund.setNetValue(StringUtils.isNotBlank(dwrs.get(idx).toString()) ? Float.parseFloat(dwrs.get(idx).toString()) : null);
                newFund.setAccumulativeTotal(StringUtils.isNotBlank(ljrs.get(idx).toString()) ? Float.parseFloat(ljrs.get(idx).toString()) : null);

                fundNetList.add(idx,newFund);
            }

        }
        catch (SocketTimeoutException ske){
            throw ske;
        }
        catch (Exception e) {
            logger.error("[获取净值失败][code:{}]",code,e);
        }
        logger.info("[code:{}][fundNetList size:{}]",code,fundNetList.size());
        return fundNetList;
    }

    @Override
    public void execute() {
        Gson jb = new Gson();
        List<FundCompany> companyList = new ArrayList<FundCompany>();
        FundCompany huashang = new FundCompany();
        huashang.setName("华商");
//        huashang.setFundPageUrl("http://market.fund123.cn/result/index/gs20-ft-sya-syb-syc-syd-sye-syf-syg-syh-nv-ljnv-sh-fc-fm-pjh-pjz-i-ic-o-p");
//        huashang.setFundTotalPage(2);
        huashang.setFundPageUrl("http://fund.eastmoney.com/company/80053204.html");
        companyList.add(huashang);
        FundCompany huaan = new FundCompany();
        huaan.setName("华安");
//        huaan.setFundPageUrl("http://market.fund123.cn/result/index/gs28-ft-sya-syb-syc-syd-sye-syf-syg-syh-nv-ljnv-sh-fc-fm-pjh-pjz-i-ic-o-p");
//        huaan.setFundTotalPage(4);
        huaan.setFundPageUrl("http://fund.eastmoney.com/company/80000228.html");
        companyList.add(huaan);
        FundCompany huaxia = new FundCompany();
        huaxia.setName("华夏");
//        huaxia.setFundPageUrl("http://market.fund123.cn/result/index/gs31-ft-sya-syb-syc-syd-sye-syf-syg-syh-nv-ljnv-sh-fc-fm-pjh-pjz-i-ic-o-p");
//        huaxia.setFundTotalPage(3);
        huaxia.setFundPageUrl("http://fund.eastmoney.com/company/80000222.html");
        companyList.add(huaxia);

        //获取净值
        for(FundCompany company: companyList){

            List<Fund> companyFundList = getAllFundsByCompany(company.getFundPageUrl());
            //过滤货币型
            List<Fund> filtered = filterMoneyFund(companyFundList);
            logger.info(jb.toJson(filtered));
            List<Fund> failList = new ArrayList<Fund>();
            for(Fund fund: filtered){

                    List<FundNet> fundNetList = null;
                    try{
                        fundNetList = getNetByCode(fund.getCode());
                        fund.setFundNetList(fundNetList);
                    }
                    catch (SocketTimeoutException ske){
                        logger.error("[获取净值超时][code:{}][name:{}]", fund.getCode(), fund.getName(), ske);
                        failList.add(fund);
                    }
                    catch (Exception e){
                        logger.error("[获取净值失败][code:{}][name:{}]", fund.getCode(), fund.getName(), e);
                    }

            }
            //超时失败的重试
            if(!failList.isEmpty()){
                logger.info("[重试列表][size:{}]",failList.size());
                for(Fund fund: failList){

                    List<FundNet> fundNetList = null;
                    try{
                        fundNetList = getNetByCode(fund.getCode());
                        fund.setFundNetList(fundNetList);
                    }
                    catch (Exception e){
                        logger.error("[重试获取净值又失败][code:{}][name:{}]",fund.getCode(),fund.getName(),e);
                    }
                }
            }
            company.setFundList(companyFundList);
        }


        exportToExcel(companyList);
    }

    @Override
    public HSSFWorkbook exportToExcel(List<FundCompany> companies) {
        String savePath = "d:/";
        final    Integer MAX_DATA_LENGTH = 1214;
        //第一步创建workbook
        HSSFWorkbook wb = new HSSFWorkbook();

        for(FundCompany company: companies){
            List<Fund> funds = company.getFundList();
            List<Fund> filtered = filterMoneyFund(funds);
            Map<Integer,List<Float>> valueMap = getNetMap(filtered);
            String companyName = company.getName();
            //第二步创建sheet
            HSSFSheet sheet = wb.createSheet(companyName);
            //第三步创建行row:添加表头0行
            HSSFRow codeRow = sheet.createRow(0);
            HSSFRow nameRow = sheet.createRow(1);
            HSSFRow typeRow = sheet.createRow(2);

            HSSFRow varRow = sheet.createRow(3);

            HSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //居中

            HSSFCell fccell = varRow.createCell(0);         //第一个单元格
            fccell.setCellValue("方差");                  //设定值
            fccell.setCellStyle(style);

            for(int fundCount = 0; fundCount < filtered.size(); fundCount ++){
                Fund singleFund = filtered.get(fundCount);

                    //第四步创建单元格
                    HSSFCell cell = codeRow.createCell(fundCount+1);         //第一个单元格
                    cell.setCellValue(singleFund.getCode());                  //设定值
                    cell.setCellStyle(style);                   //内容居中

                    HSSFCell nameCell = nameRow.createCell(fundCount+1);         //第一个单元格
                    nameCell.setCellValue(singleFund.getName());                  //设定值
                    nameCell.setCellStyle(style);                   //内容居中

                    HSSFCell typeCell = typeRow.createCell(fundCount+1);         //第一个单元格
                    typeCell.setCellValue(singleFund.getType());                  //设定值
                    typeCell.setCellStyle(style);                   //内容居中

            }

            //第五步插入数据
            for (int netCount = 0; netCount < MAX_DATA_LENGTH; netCount++) {

                //创建行
                HSSFRow dataRow = sheet.createRow(netCount+4);
                for(int fundCount = 0; fundCount < filtered.size(); fundCount ++){

                //创建单元格并且添加数据

                if(valueMap.get(fundCount).size()>netCount){
//                            logger.info("[idx:{}][i:{}]",fundCount,netCount);
                    Float cellData = valueMap.get(fundCount).get(netCount);
                    if(cellData!=null){
                        dataRow.createCell(fundCount+1).setCellValue(cellData);
                    }
                }
                }
            }



        }

        //第六步将生成excel文件保存到指定路径下
        String fileName = "fund_"+System.currentTimeMillis()+".xls";
        try {
            FileOutputStream fout = new FileOutputStream(savePath+fileName);
            wb.write(fout);
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("Excel文件生成成功...filePath:{}", savePath + fileName);

        return wb;
    }

    private Map<Integer,List<Float>> getNetMap(List<Fund> funds){
        Map<Integer,List<Float>> map = new HashMap<Integer,List<Float>>();
      try{  for(int idx=0 ; idx < funds.size(); idx++){

            Fund singleFund = funds.get(idx);

            List<FundNet> fundNetList = singleFund.getFundNetList();
          logger.info("[转换map][名称:{}]",singleFund.getName());
            try{
            List<Float> values  =  Lists.transform(fundNetList, new Function<FundNet, Float>() {
                @Override
                public Float apply(FundNet input) {
                    return (input.getAccumulativeTotal()!=null)?input.getAccumulativeTotal():null;
                }
            });
                map.put(idx,values);
            } catch (Exception e){
                logger.error("[转换map错误][名称:{}]",singleFund.getName(),e);
            }

        }
      }catch (Exception e){
          logger.error("",e);
      }
        return map;
    }

    private List<Fund> filterMoneyFund(List<Fund> allFunds){
           List<Fund> filtered = Lists.newArrayList();
        for(Fund singleFund: allFunds){
            if(singleFund.getType().equals("ETF-场内")  ||singleFund.getType().equals("货币型") || singleFund.getType().equals("短期理财")){
                logger.info("[货币型被过滤][code:{}][name:{}]",singleFund.getCode(),singleFund.getName());
            }  else{
                filtered.add(singleFund);
            }
        }
        return filtered;
    }


    public static void main(String[] args) {
        FundValueBo bo = new FundValueBoImpl();
        bo.execute();
    }
}
