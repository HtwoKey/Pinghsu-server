package com.htwokey.blog.service;

import com.htwokey.blog.entity.Comments;

import java.util.List;
import java.util.Map;

/**
 * @author hchbo
 */
public interface CommentsService {



    /**
     * 评论列表，分页
     * @param begin 开始
     * @param rows 每页记录数
     * @return 集合
     */
    List<Map<String,Object>> pageList(int begin, int rows);

    /**
     * 获取文章的评论，分页
     * @param id 文章id
     * @return list
     */
    List<Comments> getArticleComments(int id);


    /**
     * 添加评论
     * @param comments 评论对象
     * @return 添加结果
     */
    int add(Comments comments);

    /**
     * 评论总记录数
     * @return int 总记录数
     */
    int counts();

    /**
     * 删除评论
     * @param id 评论id
     * @return 执行结果
     */
    int delete(int id);

    /**
     * 将未读的评论改为已读
     * @param id 评论id
     * @return int
     */
    int changeStatus(int id);

    /**
     * 获取未阅读的评论
     * @return int
     */
    int unreadCommentsCount();
}
