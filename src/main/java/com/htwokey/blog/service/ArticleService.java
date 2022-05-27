package com.htwokey.blog.service;

import com.htwokey.blog.entity.Article;
import org.omg.CORBA.ObjectHelper;

import java.util.List;
import java.util.Map;

/**
 * 文章模块-内部数据接口
 * @author hch
 *
 */
public interface ArticleService {

    /**
     * 添加文章
     * @param article 文章
     * @param tags 标签
     * @return 1 or 0
     */
    int add(Article article,String tags);

    /**
     * 修改文章
     * @param article 文章
     * @param tags 标签
     * @return 1 or 0
     */
    int update(Article article,String tags);

    /**
     * 分页获取文章列表
     * @param begin 开始位置
     * @param rows 列数
     * @return 文章列表
     */
    List<Article> list(int begin,int rows);

    /**
     * 后端分页列表
     * @param begin 开始位置
     * @param rows 列数
     * @return list
     */
    List<Article> articleList(int begin,int rows);

    /**
     * 获取文章总条数
     * @return 总条数
     */
    int counts();

    /**
     * 根据文章id获取文章
     * @param id 文章id
     * @return 文章对象
     */
    Article getArticle(int id);

    /**
     * 按分类查询文章
     * @param id 分类id
     * @return 文章列表
     */
    List<Article> findByCategory(int id);

    /**
     * 查询文章的时间分组
     * @return 时间分组列表
     */
    List<Map<String, Object>> timeGrouping();

    /**
     * 查询文章带时间分组的文章列表
     * @return 文章列表
     */
    List<Map<String, Object>> timeLine();


    /**
     * 文章标题关键字查询
     * @param keyword 关键字
     * @return 查询结果
     */
    List<Map<String, Object>> queryKeyword(String keyword);

    /**
     * 文章阅读量加1
     * @param id 文章id
     */
    void hits(int id);

    /**
     * 按标签id进行分组查询
     * @param id 标签id
     * @return 查询结果
     */
    List<Map<String, Object>> tagGrouping(int id);

    /**
     * 删除文章
     * @param id 文章id
     * @return 1 or 0
     */
    int del(int id);

    /**
     * 查询文章评论接口状态
     * @param id 文章id
     * @return string
     */
    String selectCommentSwitch(int id);

    /**
     * 修改文章的评论开关
     * @param id 文章id
     * @param value 开关值
     * @return 1 or 0
     */
    int changeCommentSwitch(int id, String value);

    /**
     * 修改文章发布状态
     * @param id 文章id
     * @param value 状态
     * @return 1 or 0
     */
    int changeStatus(int id, String value);

    /**
     * 后端根据id查询文章
     * @param id 文章id
     * @return
     */
    Map<String,Object> get(int id);

    /**
     * 查询文的上一篇和下一篇
     * @param id
     * @return
     */
    List<Map<String, Object>> next(int id);
}
