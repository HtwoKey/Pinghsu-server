package com.htwokey.blog.mapper;

import com.htwokey.blog.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {


    /**
     * 添加分类
     * @param category 对象
     * @return 执行结果
     */
    @Insert("insert into blog_category(name,description,english)values(#{category.name},#{category.description},#{category.english})")
    int add(@Param("category")Category category)throws Exception;

    /**
     * 删除分类
     * @param id 分类id
     * @throws Exception
     */
    @Delete("delete from blog_category where cid = #{id}")
    void delete(int id)throws Exception;

    /**
     * 更新分类
     * @param category 对象
     * @return int
     */
    @Update("update blog_category set name = #{category.name},description = #{category.description},english = #{category.english} " +
            "where cid = #{category.cid}")
    int update(@Param("category")Category category)throws Exception;

    /**
     * 获取所有分类
     * @return list
     */
    @Select("SELECT cid,name FROM blog_category LIMIT 0,1000")
    List<Category> list();


    /**
     * 根据id查询分类信息
     * @param id 分类id
     * @return category
     * @throws Exception
     */
    @Select("SELECT * FROM blog_category where cid = #{id} limit 1")
    Category get(int id)throws Exception;

    /**
     * 查询总记录数
     * @return int
     */
    @Select("select count(*) from blog_category")
    int count();


    /**
     * 分页分类列表
     * @param begin 开始位置
     * @param rows 每页记录数
     * @return list
     * @throws Exception
     */
    @Select("SELECT * FROM blog_category LIMIT #{begin},#{rows}")
    List<Category> pageList(@Param("begin") int begin,@Param("rows") int rows)throws Exception;



}
