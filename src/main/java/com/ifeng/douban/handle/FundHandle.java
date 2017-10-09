package com.ifeng.douban.handle;

import cn.wanghaomiao.xpath.model.JXDocument;
import cn.wanghaomiao.xpath.model.JXNode;
import com.ifeng.douban.model.fund.Fund;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jinlin
 * Date: 16-11-10
 * Time: 下午4:52
 * To change this template use File | Settings | File Templates.
 */
public class FundHandle {
    private static Logger logger = LoggerFactory.getLogger(FundHandle.class);
    public static List<Fund> getAllFundsByCompany(){
        List<Fund> funds = new ArrayList<Fund>();
        int pageSize =4;
        String fundCompanyUrl =  "http://market.fund123.cn/result/index/gs28-ft-sya-syb-syc-syd-sye-syf-syg-syh-nv-ljnv-sh-fc-fm-pjh-pjz-i-ic-o-p";
        for(int i=0; i<pageSize; ){
            List<Fund> singlePageFunds = new ArrayList<Fund>();
            String url =  fundCompanyUrl+(++i);    //华安
            logger.info("page:"+i);
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
                    singlePageFunds.add(idx,newFund);
                }

                funds.addAll(singlePageFunds);

            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }



        logger.info("funds:"+funds);
        logger.info("funds size:"+funds.size());

        return funds;
    }

    public static void main(String[] args) {
        getAllFundsByCompany();
    }
}
