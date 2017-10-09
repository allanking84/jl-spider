/**   
* TODO(用一句话描述该文件做什么) 
* @author JinLin 基础研发组 
* 2012-7-18 下午02:17:56 
* @version V1.0   
*/

package com.ifeng.douban.handle;

import com.ifeng.douban.db.DBUtil;
import com.ifeng.douban.mapper.MovieMapper;
import com.ifeng.douban.model.Movie;
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

public class MovieHandle {
    private static Logger logger = LoggerFactory.getLogger(MovieHandle.class);
	public static String getValuable(String all){
		all = all.replaceAll("\\s+<", "<");
//		logger.info(all);
		String valuable="";
	//	String start = "<p class=\"ul first\"></p><table width=\"100%\"><tr class=\"item\"><td width=\"100\" valign=\"top\">";
		String start = "<p class=\"ul first\"></p><table width=\"100%\" class=\"\"><tr class=\"item\"><td width=\"100\" valign=\"top\">";
		int startidx = all.indexOf(start);		
		String end ="<div class=\"paginator\">";
		int endidx = all.indexOf(end);
		/*logger.debug(startidx);
		logger.debug(endidx);*/
		try {
            if(startidx > -1 && endidx > -1){
			valuable = all.substring(startidx, endidx);
            }
		} catch (Exception e) {			
			logger.error("getValuable Exception.startidx="+startidx+" endidx="+endidx,e);
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
    	String split ="<td width=\"100\" valign=\"top\">";
    	String[] arrs = input.split(split);
    	return arrs;
    }
    
    public static String getTitle(String input){
    	String start="\"  title=\"";
    	String end = "\">";
    	int startidx = input.indexOf(start);
    	int endidx = input.indexOf(end,startidx+start.length());
    	/*logger.debug(startidx);
    	logger.debug(endidx);*/
    	
    	String out="";
		try {
			out = input.substring(input.indexOf(start) + start.length(), endidx);
		} catch (Exception e) {
			logger.error("Title Format Exception:"+input,e);
		}
//		out = removeTags(out);
    	return out;
    }
    
    public static String getInfo(String input){
    	String start="<p class=\"pl\">";
    	String end = "</p><div class=\"star clearfix\">";
    	if(input.indexOf(start)==-1){
    		return "";
    	}else if (input.indexOf(end)==-1){
    		 end ="</p><span class=\"pl\">(";
    	}
    	String out="";
    	try {
			out = input.substring(input.indexOf(start) + start.length(), input
					.indexOf(end));
		} catch (Exception e) {
			logger.error("Info Format Exception:"+input,e);
		}
    	return out;
    }
    
    public static String getUrl(String input){
    	String start="<a class=\"nbg\" href=\"";    	
    	String end = "  title=\"";
    	if(input.indexOf(start)==-1){
    		logger.warn("URL is empty:"+input);
    		return "";
    	}    
    	String out = null;
    	 try {
			out = input.substring(input.indexOf(start)+start.length(),input.indexOf(end)-1);
		} catch (Exception e) {
			logger.error("URL Format Exception"+input,e);
		}
    	if(null==out){
    		out="";
    	}
    	return out;
    }
    
    public static String getRating(String input){
    	String start="<span class=\"rating_nums\">";
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
    
    public static String getViewer(String input){
    	if(input.contains("(尚未上映)")||input.contains("(评价人数不足)")
    			||input.contains("(目前无人评价)")){
    		return "0";
    	}
    	String start="<span class=\"pl\">(";
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
        final String TAG_NAME = "恐怖";      //TODO
        String tagCode = "";

        try{
            tagCode = URLEncoder.encode(TAG_NAME,"UTF-8");
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
			MovieMapper mapper = session.getMapper(MovieMapper.class);
			
//		int page = TagConstant.MOVIE_TAG_HORROR_MAX_PAGE;//TODO
        int page = MAX_PAGE;
		int start = 0;  //TODO
		for(int i= start ; i<page;i++){
		int num = 20*i;
		logger.info("Page:"+(i+1));
		String para = "start="+num+"&type=T";
	
//		String tag =TagConstant.MOVIE_TAG_HORROR_CODE; //TODO

       String result =GetHtml.get("http://movie.douban.com/tag/"+tagCode,para);
    	String value = getValuable(result);
//		String clean = removeTags(value);
        String[]arrs = split(value);
    	for(String s:arrs){   
//    		logger.debug(removeTags(s));
//    		logger.info(getTitle(s));
    		//logger.debug(getUrl(s));
//    		logger.debug(getInfo(s));
//    		logger.debug(getRating(s));
//    		logger.debug(getViewer(s));
    		Movie movie = null;
            if(s==null || s.trim().equals("")){
                logger.info("input empty,Finish!");
                return;
            }
            String url = getUrl(s);
    		if(!"".equals(url) ){
                movie = new Movie();
    			movie.setUrl(url);
    			movie.setTitle(getTitle(s));
    			movie.setInfo(getInfo(s));
    		    movie.setRating(new Float(getRating(s)));
    		    
//    		    movie.setTag(TagConstant.MOVIE_TAG_HORROR_NAME);//TODO
                movie.setTag(TAG_NAME);
                try {
                    if(getViewer(s).equals("少于10")){
                        movie.setViewer(0);
                    }   else {
					movie.setViewer(new Integer(getViewer(s)));
                    }
				} catch (NumberFormatException e) {
					logger.error("NumberFormatException",e);
					movie.setViewer(0);
				}
    		}
    		if(null!=movie){
                Movie movieDb = mapper.findByUrl(url);
                if(movieDb == null){
                    mapper.insertMovie(movie);
                }   else {
                    String dbTag = movieDb.getTag();
                    if(!dbTag.contains(TAG_NAME)){
                        movie.setTag(dbTag+","+TAG_NAME);
                    }
                    mapper.updateMovie(movie);
                }
    		/*try {
				mapper.insertMovie(movie);
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
	} catch (Exception e){
        logger.error("发生错误",e);
    }
    finally{
		session.close();
	}
			
	}
}


