package com.htwokey.blog.service;

import com.htwokey.blog.entity.Access;

import java.util.List;

/**
 * @Description 用户代理访问记录
 * @author hch
 */
public interface AccessService {

    /**
     * 添加用户访问记录
     * @param access 访问记录
     */
    void addAccess(Access access);

    /**
     * 访问记录列表，分页
     * @param begin 开始页
     * @param rows 每页记录数
     * @return list
     */
    List<Access> list(int begin, int rows);

    /**
     * 获取访问记录的条数
     * @return
     */
    int counts();
}
