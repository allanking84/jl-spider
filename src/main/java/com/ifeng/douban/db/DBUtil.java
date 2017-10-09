/**   
* TODO(用一句话描述该文件做什么) 
* @author JinLin 基础研发组 
* 2012-7-18 上午10:01:28 
* @version V1.0   
*/

package com.ifeng.douban.db;

import com.ifeng.douban.mapper.MovieMapper;
import com.ifeng.douban.model.Movie;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * TODO(这里用一句话描述这个类的作用) 
 * @author JinLin ifeng.com 基础研发组
 * 2012-7-18 上午10:01:28
 * @version V1.0
 */

/**
 * TODO(这里用一句话描述这个类的作用) 
 * @author JinLin ifeng.com 基础研发组
 * 2012-7-18 上午10:01:28 
 * @version V1.0   
 */

public class DBUtil {
public static SqlSessionFactory getSession() throws IOException{
	String resource = "mybatis_config.xml"; 
	Reader reader = Resources.getResourceAsReader(resource); 
	SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader); 
	return sqlMapper;
}
	public static void main(String[] args) {
		Movie movie = new Movie();
		movie.setTitle("建国大业");
		movie.setInfo("建国大业");
		movie.setUrl("建国大业");
		movie.setViewer(10);
		movie.setRating(6.1f);
		SqlSession session = null;
		try {
			SqlSessionFactory sqlMapper = 	getSession();
			 session = sqlMapper.openSession();
			MovieMapper mapper = session.getMapper(MovieMapper.class);
			mapper.insertMovie(movie);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			session.close();
		}
	}
}


