/**   
* TODO(用一句话描述该文件做什么) 
* @author JinLin 基础研发组 
* 2012-7-18 上午10:27:04 
* @version V1.0   
*/

package com.ifeng.douban.mapper;

import com.ifeng.douban.model.Movie;

/**
 * TODO(这里用一句话描述这个类的作用) 
 * @author JinLin ifeng.com 基础研发组
 * 2012-7-18 上午10:27:04
 * @version V1.0
 */


public interface MovieMapper {
    public void insertMovie(Movie movie);
    public Movie findByUrl(String url);
    public void updateMovie(Movie movie);
}


