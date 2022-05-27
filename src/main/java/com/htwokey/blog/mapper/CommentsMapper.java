package com.htwokey.blog.mapper;

import com.htwokey.blog.entity.Comments;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentsMapper {
    /**
     * 添加评论
     * @param c 评论对象
     * @return 执行结果
     * @throws Exception
     */
    @Insert("INSERT INTO blog_comments(name,email,url,content,ip,time,article,pid,osName,osGroup,browser,admin,status,links)" +
            "value(#{c.name},#{c.email},#{c.url},#{c.content},#{c.ip},now(),#{c.article},#{c.pid},#{c.osName},#{c.osGroup},#{c.browser},#{c.admin},#{c.status},#{c.links})")
    int add(@Param("c") Comments c)throws Exception;

    /**
     * 查询文章的所有评论
     * @param id 文章id
     * @return list
     * @throws Exception
     */
    @Select("select id,name,url,content,time,pid,osName,osGroup,browser,admin from blog_comments where article = #{id} order by time desc")
    List<Comments> lists(int id)throws Exception;

    /**
     * 删除文章的所有评论
     * @param id 文章id
     * @throws Exception
     */
    @Delete("DELETE FROM blog_comments WHERE article = #{id}")
    void delByAid(int id)throws Exception;

    /**
     * 分页查询所有评论
     * @param begin 开始位置
     * @param rows 每页记录数
     * @return list
     * @throws Exception sql异常
     */
    @Select("SELECT\n" +
            "\tt1.id,\n" +
            "\tt1.`name`,\n" +
            "\tt1.email,\n" +
            "\tt1.url,\n" +
            "\tt1.ip,\n" +
            "\tt1.time,\n" +
            "\tt1.osName,\n" +
            "\tt1.osGroup,\n" +
            "\tt1.browser,\n" +
            "\tt1.`status`,\n" +
            "\tt2.title,\n" +
            "\tt2.id aid \n"+
            "FROM\n" +
            "\tblog_comments t1\n" +
            "\tINNER JOIN blog_article t2 ON t1.article = t2.id and t1.links = 0\n" +
            "\tORDER BY t1.time DESC LIMIT #{begin},#{rows}")
    List<Map<String,Object>> pageList(@Param("begin") int begin, @Param("rows") int rows)throws Exception;

    /**
     * 查询评论总记录数
     * @return int 总记录数
     */
    @Select("select count(*) from blog_comments where links = 0")
    int Counts();

    /**
     * 删除评论
     * @param id 评论id
     * @return
     */
    @Delete("DELETE FROM blog_comments WHERE id = #{id}")
    int delete(int id)throws Exception;

    /**
     * 修改评论阅读状态
     * @param id 评论id
     * @return
     */
    @Update("UPDATE blog_comments SET status = 'true' WHERE id = #{id} ")
    int changeStatus(int id)throws Exception;

    /**
     * 查询友链申请列表
     * @return
     */
    @Select("select * from blog_comments where links = 1 ORDER BY id DESC LIMIT #{begin},#{rows}")
    List<Comments> applyList(@Param("begin") int begin, @Param("rows") int rows)throws Exception;

    /**
     * 查询友链申请条数
     * @return
     */
    @Select("select count(*) from blog_comments where links = 1")
    int applyListCounts();

    /**
     * 查询未读的评论条数
     * @return
     */
    @Select("select Count(*) from blog_comments where links = 0 and status = 'false'")
    int unreadMessages();

    /**
     * 未处理的友链申请条数
     * @return
     */
    @Select("select Count(*) from blog_comments where links = 1 and status = 'false'")
    int unreadLinkApplication();
}
