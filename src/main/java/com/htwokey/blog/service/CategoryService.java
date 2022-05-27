package com.htwokey.blog.service;

import com.htwokey.blog.entity.Category;

import java.util.List;

/**
 * 分类模块-内部数据接口
 * @author hch
 */
public interface CategoryService {

    /**
     * 获取所有分类
     * @return list
     */
    List<Category> list();

    /**
     * 分类分页列表
     * @param begin 起始位置
     * @param rows 每页记录数
     * @return list
     */
    List<Category> pageList(int begin, int rows);

    /**
     * 获取分类总记录数
     * @return int
     */
    int counts();

    /**
     * 添加分类
     * @param category 参数
     * @return int
     */
    int add(Category category);

    /**
     * 删除分类
     * @param id 分类id
     * @return 执行结果
     */
    int delete(int id);

    /**
     * 更新分类
     * @param category 分类对象
     * @return 执行结果
     */
    int update(Category category);

    /**
     * 根据id查询分类信息
     * @param id 分类id
     * @return 分类对象
     */
    Category get(int id);
}
