package com.htwokey.blog.service;

import com.htwokey.blog.entity.Httplog;

import java.util.List;

/**
 * @author by hchbo
 * @Description TODO
 * @Date 2019-06-17 13:48
 */
public interface HttplogService {

    /**
     * 添加浏览记录
     * @param httplog log对象
     * @return int 返回添加结果
     */
    int add(Httplog httplog);

    /**
     * 获取浏览记录列表
     * @param begin 查询参数
     * @param rows 查询参数
     * @return 查询结果
     */
    List<Httplog> list(int begin,int rows);

    /**
     * 查询总记录数
     * @return 查询结果
     */
    int count();
}
