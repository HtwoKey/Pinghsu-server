package com.htwokey.blog.mapper;

import com.htwokey.blog.entity.Httplog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author by hchbo
 * @Description 用户浏览记录
 * @Date 2019-06-17 13:46
 */

@Mapper
public interface HttplogMapper {

    /**
     * 添加请求记录
     * @param http
     * @return
     * @throws Exception
     */
    @Insert("insert into blog_http(ip,url,method,params,time)values" +
            "(#{http.ip},#{http.url},#{http.method},#{http.params},now())")
    int add(@Param("http") Httplog http)throws Exception;

    /**
     * 分页查询请求记录
     * @param begin
     * @param rows
     * @return
     * @throws Exception
     */
    @Select("select * from blog_http order by id desc LIMIT #{begin},#{rows} ")
    List<Httplog> list(@Param("begin")int begin,@Param("rows")int rows)throws Exception;

    /**
     * 查询总条数
     * @return
     */
    @Select("select count(*) from blog_http")
    int count();
}
