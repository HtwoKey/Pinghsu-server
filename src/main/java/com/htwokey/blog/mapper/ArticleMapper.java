package com.htwokey.blog.mapper;

import com.htwokey.blog.entity.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author hch
 * @date 2019-12-13 0:04
 */

@Mapper
public interface ArticleMapper {

    /**
     * 添加文章
     * @param a 文章对象
     * @return 添加结果
     * @throws Exception sqlException
     */
    @Insert("INSERT INTO blog_article(title,slug,contentHtml,contentMkd,created,modified,status,cname,category,commentSwitch,imageCover) " +
            "VALUE(#{a.title},#{a.slug},#{a.contentHtml},#{a.contentMkd},now(),now(),#{a.status},#{a.cname},#{a.category},#{a.commentSwitch},#{a.imageCover}) ")
    @Options(useGeneratedKeys = true, keyProperty = "a.id", keyColumn = "id")
    int add(@Param("a") Article a) throws Exception;


    /**
     * 删除文章
     * @param id 文章id
     * @return 执行结果
     * @throws Exception sqlException
     */
    @Delete("DELETE FROM blog_article WHERE id = #{id}")
    int del(int id)throws Exception;

    /**
     * 更新文章
     * @param a article
     * @return int
     * @throws Exception sqlException
     */
    @Update("UPDATE blog_article set title = #{a.title},slug = #{a.slug},contentHtml = #{a.contentHtml},contentMkd = #{a.contentMkd},modified = now()," +
            "status = #{a.status},category = #{a.category},cname=#{a.cname},commentSwitch = #{a.commentSwitch},imageCover = #{a.imageCover} WHERE id = #{a.id}")
    int update(@Param("a") Article a)throws Exception;

    /**
     * 查询总记录数
     * @return 总记录数
     */
    @Select("SELECT COUNT(*) FROM blog_article ")
    int counts();

    /**
     * 前端分页查询
     * @param begin 开始位置
     * @param rows 分页数
     * @return 查询结果
     * @throws Exception sqlException
     */
    @Select("SELECT id,title,slug,category,cname,imageCover FROM blog_article where `status` = 'true' order by id desc limit #{begin},#{rows}")
    List<Article> list(@Param("begin") int begin, @Param("rows") int rows)throws Exception;

    /**
     * 后端分页列表
     * @param begin 开始位置
     * @param rows 分页数
     * @return 查询结果
     * @throws Exception sqlException
     */
    @Select("select id,title,created,modified,hits,status,cname,commentsnum,commentSwitch from blog_article order by id desc limit #{begin},#{rows}")
    List<Article> articleList(@Param("begin") int begin, @Param("rows") int rows)throws Exception;

    /**
     * 前端根据id查询
     * @param id 文章id
     * @return 文章对象
     * @throws Exception sqlException
     */
    @Select("SELECT id,created,cname,imageCover,contentHtml,title,hits,commentSwitch,modified,category,commentsnum,slug FROM blog_article WHERE id = #{id} and `status` = 'true' limit 1")
    Article getArticle(int id)throws Exception;

    /**
     * 后端文章编辑回显查询
     * @param id 文章id
     * @return 文章对象
     * @throws Exception sqlException
     */
    @Select("SELECT * FROM blog_article WHERE id = #{id} limit 1")
    Article get(int id)throws Exception;

    /**
     * 根据分类id查看文章
     * @param id 分类id
     * @return 查询结果
     * @throws Exception sqlException
     */
    @Select("SELECT id,title,created,cname FROM blog_article WHERE category = #{id} ORDER BY created DESC")
    List<Article> findByCategory(int id) throws Exception;

    /**
     * 文章基于时间分组的列表
     * @return 文章分组列表
     */
    @Select("SELECT DATE_FORMAT(created, '%Y年%m月') month,id,title,created FROM blog_article ORDER BY created DESC")
    List<Map<String, Object>> timeLine();

    /**
     * 文章时间分组列表
     * @return 时间分组列表
     */
    @Select("SELECT DATE_FORMAT(created, '%Y年%m月')as `month` FROM blog_article GROUP BY `month` ORDER BY `month` DESC")
    List<Map<String,Object>> timeGrouping();

    /**
     * 文章标题模糊查询
     * @param name 文章标题
     * @return 查询结果
     * @throws Exception sqlException
     */
    @Select("SELECT `id` AS value, `title` as `name` FROM blog_article WHERE title LIKE concat('%',#{name},'%') LIMIT 10 ")
    List<Map<String,Object>> queryKeyword(String name) throws Exception;

    /**
     * 文章浏览次数加1
     * @param id 文章id
     * @throws Exception sqlException
     */
    @Update("UPDATE blog_article SET hits = hits+1 WHERE id = #{id}")
    void hits(int id)throws Exception;

    /**
     * 文章评论数量加1
     * @param id 文章id
     * @throws Exception sqlException
     */
    @Update("UPDATE blog_article SET commentsnum = commentsnum+1 WHERE id = #{id}")
    void addComments(int id) throws Exception;

    /**
     * 查询单个标签的文章分组
     * @param tid 标签id
     * @return 分组列表
     * @throws Exception sqlException
     */
    @Select("SELECT a.id,a.title,a.created,a.cname,t.`name` as tag FROM blog_article a\n" +
            "LEFT JOIN blog_article_tags t1 ON a.id = t1.aid LEFT JOIN blog_tags t ON t1.tid = t.id WHERE t.id = #{tid}")
    List<Map<String,Object>> tagGrouping(int tid)throws Exception;

    /**
     *  查询文章评论接口状态
     * @param id 文章id
     * @return 状态值
     * @throws Exception sqlException
     */
    @Select("select commentSwitch from blog_article where id = #{id} limit 1")
    String selectCommentSwitch(int id)throws Exception;

    /**
     * 设置文章评论开关
     * @param id 文章id
     * @param value 状态值
     * @return 执行结果
     * @throws Exception sqlException
     */
    @Update("UPDATE blog_article SET commentSwitch = #{value} WHERE id = #{id}")
    int changeCommentSwitch(@Param("id") int id,@Param("value") String value)throws Exception;

    /**
     * 设置文章发布状态
     * @param id 文章id
     * @param value 状态值
     * @return 执行结果
     * @throws Exception sqlException
     */
    @Update("UPDATE blog_article SET `status` = #{value} WHERE id = #{id}")
    int changeStatus(@Param("id") int id,@Param("value") String value)throws Exception;

    /**
     * 查询文章的上一篇和下一篇
     * @param id 文章id
     * @return 查询结果
     * @throws Exception sqlException
     */
    @Select("SELECT\n" +
            "\tid, title \n" +
            "FROM\n" +
            "\tblog_article \n" +
            "WHERE\n" +
            "\tid = ( SELECT MIN( id ) FROM blog_article WHERE id > #{id} and `status` = 'true') \n" +
            "\tOR id = ( SELECT MAX( id ) FROM blog_article WHERE id < #{id} and `status` = 'true' )")
    List<Map<String,Object>> next(int id)throws Exception;
}
