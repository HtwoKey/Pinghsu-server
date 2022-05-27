package com.htwokey.blog.mapper;

import com.htwokey.blog.entity.Links;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author by hchbo
 * @Classname LinksMapper
 * @Description TODO
 * @Date 2019-05-29 10:13
 */
@Mapper
public interface LinksMapper {


    @Select("select count(*) from blog_links")
    int Counts();

    @Select("SELECT * FROM blog_links LIMIT #{begin},#{rows}")
    List<Links> pageList(@Param("begin") int begin,@Param("rows") int rows)throws Exception;

    @Insert("insert into blog_links(name,url,time,status)values(#{links.name},#{links.url},now(),#{links.status})")
    int add(@Param("links") Links links)throws Exception;

    @Delete("delete from blog_links where id = #{id}")
    int delete(int id)throws Exception;

    @Update("update blog_links set name=#{links.name},url=#{links.url},status=#{links.status} where id = #{links.id}")
    int update(@Param("links") Links links)throws Exception;

    @Select("select * from blog_links")
    List<Links> list();
}
