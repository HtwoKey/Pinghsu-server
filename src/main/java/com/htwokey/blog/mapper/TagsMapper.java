package com.htwokey.blog.mapper;

import com.htwokey.blog.entity.Tags;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface TagsMapper {

    //查询所有标签
    @Select("SELECT * FROM blog_tags ")
    List<Tags> list() throws Exception;

    //查询文章引用的标签
    @Select("SELECT t1.id,t1.`name` from blog_tags t1 LEFT JOIN blog_article_tags t2 on t1.id = t2.tid WHERE t2.aid = #{id}")
    List<Tags> findByArticleId(int id) throws Exception;

    //添加标签
    @Insert("INSERT INTO `blog_tags`(name) VALUES (#{tags.name})")
    int addTags(@Param("tags") Tags tags) throws Exception;

    //删除标签
    @Delete("DELETE FROM blog_tags WHERE id = #{id} ")
    int delTags(int id) throws Exception;

    //查询标签引用次数分组
    @Select("SELECT MAX(t.`name`) as name ,a.tid,COUNT(*) as con from blog_tags t,blog_article_tags a WHERE t.id = a.tid GROUP BY a.tid")
    List<Map<String,Object>> findTagQuote() throws Exception;

    //添加文章标签
    @Insert("INSERT INTO blog_article_tags( `tid`, `aid`) VALUES (#{tag},#{aId})")
    void addtags(@Param("aId") int aId, @Param("tag") int tag) throws Exception;


    // 文章页删除标签的引用
    @Delete("DELETE FROM blog_article_tags WHERE `aid`=#{id}")
    void deleteByAid(int id) throws Exception;


    // 查询文章使用的标签
    @Select("SELECT tid FROM blog_article_tags where aid = #{id}")
    List<Integer> findArticle_tagsByAid(int id)throws Exception;

    /**
     * 修改标签名称
     * @param tags 标签对象
     * @return
     * @throws Exception
     */
    @Update("update blog_tags set name = #{tags.name} where id = #{tags.id}")
    int update(@Param("tags") Tags tags) throws Exception;

    /**
     * 分页查询标签列表
     * @param begin 开始位置
     * @param rows 每页记录数
     * @return
     */
    @Select("select * from blog_tags limit #{begin},#{rows}")
    List<Tags> pageList(@Param("begin") int begin,@Param("rows") int rows)throws Exception;

    /**
     * 查询标签总条数
     * @return
     */
    @Select("select count(*) from blog_tags")
    int Counts();

    /**
     * 标签页删除文章的引用
     * @param id
     */
    @Delete("DELETE FROM blog_article_tags WHERE `tid`=#{id}")
    void deleteByTid(int id)throws Exception;
}
