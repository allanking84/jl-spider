/**   
* TODO(用一句话描述该文件做什么) 
* @author JinLin 基础研发组 
* 2012-7-18 上午10:27:04 
* @version V1.0   
*/

package com.ifeng.douban.mapper;

import com.ifeng.douban.model.Book;

/**
 * TODO(这里用一句话描述这个类的作用) 
 * @author JinLin ifeng.com 基础研发组
 * 2012-7-18 上午10:27:04
 * @version V1.0
 */


public interface BookMapper {
    public void insertBook(Book book);
    public Book findByUrl(String url);
    public void updateBook(Book movie);
}


