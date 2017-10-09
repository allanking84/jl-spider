package com.jlspider.homelink.bo.impl;

import cn.wanghaomiao.xpath.model.JXDocument;
import cn.wanghaomiao.xpath.model.JXNode;
import com.google.gson.Gson;
import com.ifeng.douban.model.fund.Fund;

import com.jlspider.homelink.model.House;
import com.jlspider.homelink.bo.HousePriceBo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jinlin
 * Date: 16-11-11
 * Time: 上午11:31
 * To change this template use File | Settings | File Templates.
 */
public class HousePriceBoImpl implements HousePriceBo {
    private static Logger logger = LoggerFactory.getLogger(HousePriceBoImpl.class);

    @Override
    public List<House> grepHouseInfo(String url) {
        List<House> houseList = new ArrayList<House>();
        JXDocument jxDocument = null;
        try {
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0").get();
            jxDocument = new JXDocument(doc);

            String djxpath = "//html/body/div[4]/div[1]/ul//div[1]/div[6]/div[2]/span/text()";        //单价

            System.out.println("current xpath:" + djxpath);
            List<JXNode> djrs = jxDocument.selN(djxpath);


            String titlexpath = "//html/body/div[4]/div[1]/ul//div[1]/div[1]/a/text()";        //标题

            System.out.println("current xpath:" + titlexpath);
            List<JXNode> titlers = jxDocument.selN(titlexpath);

            String infoxpath = "//html/body/div[4]/div[1]/ul//div[1]/div[2]/div/text()";        //信息

            System.out.println("current xpath:" + infoxpath);
            List<JXNode> infors = jxDocument.selN(infoxpath);

            String zjxpath = "//html/body/div[4]/div[1]/ul//div[1]/div[6]/div[1]/span/text()";        //总价

            System.out.println("current xpath:" + zjxpath);
            List<JXNode> zjrs = jxDocument.selN(zjxpath);

            for (int idx = 0; idx < djrs.size(); idx++) {
                JXNode n = djrs.get(idx);
                if (!n.isText()) {
                    int index = n.getElement().siblingIndex();
                    logger.info(index + "");
                }
                House newObj = new House();
                newObj.setUnitPrice(getUnitPrice(djrs.get(idx).toString()));
                newObj.setTitle(titlers.get(idx).toString());
                newObj.setHouseInfo(infors.get(idx).toString());
                newObj.setTotalPrice(Integer.parseInt(zjrs.get(idx).toString()));
                houseList.add(newObj);
            }



        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return houseList;
    }

    @Override
    public void recommendPrice(List<House> houseList) {
        Float recommendRate = 0.7F;
        int i =1;
        List<Integer> priceList = new ArrayList<Integer>();
        for(House single: houseList){

            logger.info("序号："+i);
            i++;
            Integer singlePrice = single.getUnitPrice();
            logger.info("单价："+singlePrice);
            priceList.add(singlePrice);
        }
        Collections.sort(priceList);
        Integer recommendPosition = new Float(recommendRate*Float.parseFloat(houseList.size()+"")).intValue();
        logger.info("recommendPosition:"+ recommendPosition);
        logger.info("推荐单价:"+priceList.get(recommendPosition) +"~" +priceList.get(recommendPosition+1));
    }

    private Integer getUnitPrice(String input){
        //单价54903元/平米
        int startidx = input.indexOf("单价")+2;
        int endidx = input.indexOf("元/平米");
        String act = input.substring(startidx,endidx);
        return Integer.parseInt(act);
    }

    public static void main(String[] args) {
        HousePriceBo bo = new HousePriceBoImpl();
       List<House> houses = bo.grepHouseInfo("http://bj.lianjia.com/ershoufang/l2c1111045269268rs%E9%B8%BF%E5%9D%A4%E7%90%86%E6%83%B3%E5%9F%8E%E7%A4%BC%E5%9F%9F%E5%BA%9C/");
        bo.recommendPrice(houses);
       Gson jb = new Gson();
        logger.info("HOUSE:"+jb.toJson(houses));
    }
}
