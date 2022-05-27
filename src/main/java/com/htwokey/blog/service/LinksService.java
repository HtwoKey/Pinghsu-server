package com.htwokey.blog.service;

import com.htwokey.blog.entity.Comments;
import com.htwokey.blog.entity.Links;

import java.util.List;

/**
 * @author by hchbo
 * @Classname LinksService
 * @Description 友链，业务层数据接口
 * @Date 2019-05-29 10:09
 */
public interface LinksService {


    /**
     * 友链总记录数
     * @return int count
     */
    int Counts();

    /**
     * 友链分页列表
     * @param begin 开始位置
     * @param rows 每页记录数
     * @return List<Links>
     */
    List<Links> pageList(int begin, int rows);

    /**
     * 添加友链
     * @param links 友链对象
     * @return 执行结果
     */
    int add(Links links);

    /**
     * 删除友链
     * @param id 友链id
     * @return 执行结果
     */
    int delete(int id);

    /**
     * 修改友链
     * @param links 友链对象
     * @return 执行结果
     */
    int update(Links links);

    /**
     * 添加友链申请
     * @param comments 评论对象
     * @return 执行结果
     */
    int addComments(Comments comments);

    /**
     * 前端友链列表
     * @return List<Links>
     */
    List<Links> list();


    /**
     * 友链申请列表
     * @return
     */
    List<Comments> applyList(int begin,int rows);

    /**
     * 查询友链申请列表条数
     * @return
     */
    int applyListCounts();

    /**
     * 查询没有处理的友链申请的条数
     * @return
     */
    int untreatedLinks();
}
