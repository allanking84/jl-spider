/**   
* TODO(用一句话描述该文件做什么) 
* @author JinLin 基础研发组 
* 2012-7-18 下午02:17:56 
* @version V1.0   
*/

package com.ifeng.douban.handle;

import com.ifeng.douban.db.DBUtil;
import com.ifeng.douban.mapper.BookMapper;
import com.ifeng.douban.model.Book;
import com.ifeng.douban.util.GetHtml;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/** 
 * TODO(这里用一句话描述这个类的作用) 
 * @author JinLin ifeng.com 基础研发组
 * 2012-7-18 下午02:17:56 
 * @version V1.0   
 */

public class BookHandle {
    private static Logger logger = LoggerFactory.getLogger(BookHandle.class);
	public static String getValuable(String all){
//		logger.info(all);
		String valuable="";
//		String start = "<p class=\"ul first\"></p><table width=\"100%\"><tr class=\"item\"><td width=\"100\" valign=\"top\">";
		String start = "<li class=\"subject-item\">" ;
		int startidx = all.indexOf(start);		
		String end ="<div class=\"paginator\">";
		int endidx = all.indexOf(end);
        if(endidx == -1){
            return null;
        }
	/*	logger.debug(startidx);
		logger.debug(endidx);*/
		try {
			valuable = all.substring(startidx, endidx);
		} catch (Exception e) {			
			logger.error("getValuable Exception",e);
		}
		return valuable;		

	}
	
	/** 
     * 删除input字符串中的html格式 
     *  
     * @param input 
     * @param length 
     * @return 
     */  
    public static String splitAndFilterString(String input, int length) {  
        if (input == null || input.trim().equals("")) {  
            return "";  
        }  
        // 去掉所有html元素,  
        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(  
                "<[^>]*>", "");  
        str = str.replaceAll("[(/>)<]", "");  
        int len = str.length();  
        if (len <= length) {  
            return str;  
        } else {  
            str = str.substring(0, length);  
            str += "......";  
        }  
        return str;  
    }  
    
    public static String removeTags(String input){
    	 if (input == null || input.trim().equals("")) {  
             return "";  
         } 
    	input = input.trim();
    	 String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(  
                 "<[^>]*>", "");  
//         str = str.replaceAll("[(/>)<]", "");  
    	return str;
    }
    
    public static String[] split(String input){
//    	String split ="<td width=\"100\" valign=\"top\">";
    	String split ="<li class=\"subject-item\">";
    	String[] arrs = input.split(split);
        if(!input.contains(split)){
            logger.info("split return:"+arrs);
        }
    	return arrs;
    }
    
    public static String getTitle(String input){
    	String start="\" title=\"";
//    	String end = "\" >";
    	String end = "\" \n  onclick=\"moreurl(this,";
    	int startidx = input.indexOf(start) + start.length();
    	int endidx = input.indexOf(end,startidx);
    	/*logger.debug(startidx);
    	logger.debug(endidx);*/
    	
    	String out="";
		try {
			out = input.substring(startidx, endidx);
		} catch (Exception e) {
			logger.error("Title Format Exception:"+input,e);
		}
//		out = removeTags(out);
    	return out;
    }
    
    public static String getInfo(String input){
//    	String start="<p class=\"pl\">";
    	String start="<div class=\"pub\">";
    	
//    	String end = "</p><div class=\"star clearfix\">";
    	String end = "</div>";
    	
    	int startidx = input.indexOf(start) + start.length();
    	int endidx = input.indexOf(end,startidx);
    	
    	if(input.indexOf(start)==-1){
    		return "";
    	}else if (input.indexOf(end)==-1){
    		 end ="</p><span class=\"pl\">(";
    	}
    	String out="";
    	try {
			out = input.substring(startidx, endidx).trim();
		} catch (Exception e) {
			logger.error("Info Format Exception:"+input,e);
		}
    	return out;
    }
    
    public static String getUrl(String input){
    	String start="<a class=\"nbg\" href=\"";
//    	String end = "  title=\"";
    	String end = " \n  onclick=\"moreurl(this,";
    					         
    	if(input.indexOf(start)==-1){
    		logger.warn("URL is empty:"+input);
    		return "";
    	}    
    	int startidx = input.indexOf(start)+start.length();
    	int endidx = input.indexOf(end,startidx)-1;
    	String out = input.substring(startidx,endidx);
    	if(null==out){
    		out="";
    	}
    	return out;
    }
    
    public static String getRating(String input){
    	String start="<span class=\"rating_nums\">";
//    	String end = "</span><span class=\"pl\">(";
    	String end = "</span>\n\n    <span class=\"pl\">";
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
    
    public static String getViewer(String input){
    	if(input.contains("(少于10人评价)")||input.contains("(评价人数不足)")
    			||input.contains("(目前无人评价)")){
    		return "0";
    	}
    	String start="<span class=\"pl\">\n        (";
    	String end = "人评价)";
    	if(input.indexOf(start)==-1){
    		return "0";
    	}
    	String out="";
    	try {
			out = input.substring(input.indexOf(start) + start.length(), input
					.indexOf(end));
		} catch (Exception e) {
			logger.error("Viewer Format Exception:"+input,e);
		}
		if(null==out || "".equals(out)){
			out="0";
		}
    	return out;
    }
    
    public static void main(String[] args) {
        final int MAX_PAGE = 1000;
        final String TAG_NAME = "编程";
        String tagCode = "";

        try{
            tagCode = URLEncoder.encode(TAG_NAME, "UTF-8");
            logger.info("tagName:"+TAG_NAME+" urlencoder tagCode:"+tagCode);
        }catch (UnsupportedEncodingException uee){
            logger.error("UnsupportedEncodingException",uee);
        }

    	SqlSession session = null;
		
			SqlSessionFactory sqlMapper=null;
			
				try {
					sqlMapper = DBUtil.getSession();
				} catch (IOException e1) {
					logger.error("get Session Error",e1);
				}
	try{		
			 session = sqlMapper.openSession();
			BookMapper mapper = session.getMapper(BookMapper.class);
			
//		int page = TagConstant.BOOK_TAG_BUSINESS_MAX_PAGE;//TODO
        int page = MAX_PAGE;
		int start = 0;  //TODO
		for(int i= start ; i<page;i++){
		int num = 20*i;
		logger.info("Page:"+(i+1));
		String para = "start="+num+"&type=T";
	
//		String tag =TagConstant.BOOK_TAG_BUSINESS_CODE; //TODO


		String result =GetHtml.get("http://book.douban.com/tag/"+tagCode,para);
		logger.debug(result);
    	String value = getValuable(result);
            if(value == null){
                logger.info("input empty,Finish!");
                return;
            }
//		String clean = removeTags(value);
    	String[]arrs = split(value);
    	for(String s:arrs){   
//    		logger.debug(removeTags(s));
//    		logger.debug(getUrl(s));
//    		logger.debug(getTitle(s));    		
//    		logger.debug(getInfo(s));
//    		logger.debug(getRating(s));
//    		logger.debug(getViewer(s));
    		Book book = null;
            if(s==null || s.trim().equals("")){
               continue;
            }
            String url = getUrl(s);
    		if(!"".equals(url) ){
    			 book = new Book();
    			book.setUrl(url);
    			book.setTitle(getTitle(s));
    			book.setInfo(getInfo(s));
    		    book.setRating(new Float(getRating(s)));
    		    
//    		    book.setTag(TagConstant.BOOK_TAG_BUSINESS_NAME);//TODO
                book.setTag(TAG_NAME);
    			try {
					book.setViewer(new Integer(getViewer(s)));
				} catch (NumberFormatException e) {
					logger.error("NumberFormatException",e);
					book.setViewer(0);
				}
    		}
    		if(null!=book){
                Book bookDb = mapper.findByUrl(url);
                if(bookDb == null){
                    mapper.insertBook(book);
                }   else {
                    String dbTag = bookDb.getTag();
                    if(!dbTag.contains(TAG_NAME)){
                        book.setTag(dbTag+","+TAG_NAME);
                    }
                    mapper.updateBook(book);
                }


    		/*try {
				mapper.insertBook(book);
			} 	catch (PersistenceException pe) {
				logger.warn("Duplicate Record");
			}*/

    		}
    	}
		try {
		Thread.sleep(10000);
	} catch (InterruptedException e) {
		logger.error("Thread InterruptedException",e);
	}
			} //end for page
	}finally{
		session.close();
	}
			
	}
}


