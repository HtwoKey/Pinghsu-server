package com.htwokey.blog.mapper;

import com.htwokey.blog.entity.Access;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author hchbo
 */
@Mapper
public interface AccessMapper {

    /** 添加用户访问记录 */
    @Insert("insert into blog_access(ip,accessTime,browserName,browserType,browserManufacturer,osName,osGroup,osDeviceType,userAgent)" +
            "VALUES(#{access.ip},now(),#{access.browserName},#{access.browserType},#{access.browserManufacturer}," +
            "#{access.osName},#{access.osGroup},#{access.osDeviceType},#{access.userAgent})")
    int add(@Param("access") Access access) throws Exception;


    /**访问记录列表*/
    @Select("SELECT * FROM `blog_access` order by id desc LIMIT #{begin},#{rows}")
    List<Access> list(@Param("begin") int begin, @Param("rows") int rows)throws Exception;

    /**查询总记录数*/
    @Select("SELECT count(*) FROM `blog_access`")
    int Counts()throws Exception;
}
