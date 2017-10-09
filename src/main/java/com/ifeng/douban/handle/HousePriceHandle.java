package com.ifeng.douban.handle;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jinlin
 * Date: 16-9-21
 * Time: 下午12:39
 * To change this template use File | Settings | File Templates.
 */
public class HousePriceHandle {
    private static Logger logger = LoggerFactory.getLogger(MovieHandle.class);


     public static String getValuable(String all){

        String valuable="";

        String start = "<ul class=\"sellListContent\" log-mod=\"list\">";
        int startidx = all.indexOf(start);
        String end ="<div class=\"bigImgList\" style=\"display: none;\" log-mod=\"list\">";
        int endidx = all.indexOf(end);

        try {
            if(startidx > -1 && endidx > -1){
                valuable = all.substring(startidx, endidx);
            }
        } catch (Exception e) {
            logger.error("getValuable Exception.startidx="+startidx+" endidx="+endidx,e);
        }
//         logger.info("valuable:"+valuable);
        return valuable;

    }

    public static String[] split(String input){
        String split ="关注</span></div></li><li class=\"clear\"><a class=\"img\"";
        String[] arrs = input.split(split);
        return arrs;
    }

    public static void printPrice() throws Exception{
         Float recommendRate = 0.7F;
    String url = "";
    url = "http://bj.lianjia.com/ershoufang/l2c1111045269268rs%E9%B8%BF%E5%9D%A4%E7%90%86%E6%83%B3%E5%9F%8E%E7%A4%BC%E5%9F%9F%E5%BA%9C/";

         String result = getHtml(url);
//        logger.info("result:"+result);
        String valuable = getValuable(result);
        String[] listStr = split(valuable);
        int i =1;
        List<Integer> priceList = new ArrayList<Integer>();
        for(String single: listStr){

            logger.info("序号："+i);
            i++;
            String singlePrice = getPrice(single);
            logger.info("单价："+singlePrice);
            priceList.add(Integer.parseInt(singlePrice));
        }
        Collections.sort(priceList);
        Integer recommendPosition = new Float(recommendRate*Float.parseFloat(listStr.length+"")).intValue();
        logger.info("recommendPosition:"+ recommendPosition);
        logger.info("推荐单价:"+priceList.get(recommendPosition) +"~" +priceList.get(recommendPosition+1));
    }


    public static String getPrice(String input){
        String start="data-price=\"";
        String end = "\"><span>单价";
        if(input.indexOf(start)==-1){
            return "unknown";
        }
        String out="";
        try {
            out = input.substring(input.indexOf(start) + start.length(), input
                    .indexOf(end));
        } catch (Exception e) {
            logger.error("Price Exception:"+input,e);
        }
        if(null==out || "".equals(out)){
            out="unknown";
        }
        return out;
    }
    public static String getTitle(String input){
        String start=" data-el=\"ershoufang\">";
        String end = "</span><span class=\"pl\">(";
        if(input.indexOf(start)==-1){
            return "0.0";
        }
        String out="";
        try {
            out = input.substring(input.indexOf(start) + start.length(), input
                    .indexOf(end));
        } catch (Exception e) {
            logger.error("Rating Format Exception:"+input,e);
        }
        if(null==out || "".equals(out)){
            out="0.0";
        }
        return out;
    }

    public static String getHtml(String url) throws Exception{
        HttpClient client = new HttpClient();
        HttpMethod method = null;
        method = new GetMethod(url);
        HttpMethodParams params = new HttpMethodParams();
        params.setContentCharset("UTF-8");
        method.setParams(params);
        client.executeMethod(method);


        return  method.getResponseBodyAsString();
    }

    public static void main(String[] args)throws Exception {
        printPrice();
    }
}
