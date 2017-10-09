/**   
* TODO(用一句话描述该文件做什么) 
* @author JinLin 基础研发组 
* 2012-7-18 上午09:43:53 
* @version V1.0   
*/

package com.ifeng.douban.model;
/**
 * TODO(这里用一句话描述这个类的作用) 
 * @author JinLin ifeng.com 基础研发组
 * 2012-7-18 上午09:43:53
 * @version V1.0
 */


public class Book {
private String title;
private String info;
private Integer viewer;
private String url;
private Float rating;
private String tag;


public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getInfo() {
	return info;
}
public void setInfo(String info) {
	this.info = info;
}
public Integer getViewer() {
	return viewer;
}
public void setViewer(Integer viewer) {
	this.viewer = viewer;
}
public Float getRating() {
	return rating;
}
public void setRating(Float rating) {
	this.rating = rating;
}
public String getTag() {
	return tag;
}
public void setTag(String tag) {
	this.tag = tag;
}


}


