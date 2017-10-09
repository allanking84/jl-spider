package com.ifeng.douban.util;
/**   
* TODO(用一句话描述该文件做什么) 
* @author JinLin 基础研发组 
* 2012-7-17 下午04:45:46 
* @version V1.0   
*/


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;


/** 
 * TODO(这里用一句话描述这个类的作用) 
 * @author JinLin ifeng.com 基础研发组
 * 2012-7-17 下午04:45:46 
 * @version V1.0   
 */

public class GetHtml {
	/**
	 * 
	 * @param url
	 * @param queryString
	 * @return
	 */
	public static String get(String url, String queryString){
		String response = null;
	    HttpClient client = new HttpClient();
	    HttpMethod method = null;
	    try {
	    	if (queryString != null && !queryString.equals("")){
	    		if(url.indexOf("?")>0){
	    			url += "&"+queryString;
	    		}else{
	    			url += "?"+queryString;
	    		}
	        }
	    	method = new GetMethod(url);
	    	method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.12) Gecko/2009070611 Firefox/3.0.12");
	    	method.setRequestHeader("Cookie","__utma=223695111.666056219.1336452263.1361418341.1361422945.187; __utmz=223695111.1351479520.113.2.utmcsr=baidu|utmccn=(organic)|utmcmd=organic|utmctr=%E8%B1%86%E7%93%A3%E7%94%B5%E8%A7%86%E5%89%A7%E6%8E%92%E8%A1%8C"); 
	        client.executeMethod(method);
	        response = method.getResponseBodyAsString();
	    } catch(Exception e){
	    	e.printStackTrace();
		} finally {
			method.releaseConnection();
	    }
	    return response;
	}
	

	
	public static void main(String[] args) {
		//http://movie.douban.com/tag/%E4%B8%AD%E5%9B%BD?start=0&type=T
		String result = get("http://movie.douban.com/tag/%E4%B8%AD%E5%9B%BD?start=0","start=0&type=T");		
//		String value = getValuable(result);
//		String clean = removeTags(value);
		System.out.println(result);
	}
}


