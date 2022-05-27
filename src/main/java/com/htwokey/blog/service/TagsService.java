package com.htwokey.blog.service;

import com.htwokey.blog.entity.Tags;

import java.util.List;
import java.util.Map;

public interface TagsService {
    /**
     * 获取所有标签
     * @return 标签列表
     */
    List<Tags> list();

    /**
     * 查询文章引用的标签
     * @param id 文章id
     * @return 标签
     */
    List<Tags> findByArticleId(int id);

    /**
     * 添加标签
     * @param tags 标签
     * @return count
     */
    int addTags(Tags tags);

    /**
     * 删除标签
     * @param id 标签id
     * @return count
     */
    int delTags(int id);

    /**
     * 按标签引用次数分组
     * @return 查询结果
     */
    List<Map<String, Object>> tagGroup();

    /**
     * 修改标签
     * @param tags 标签对象
     * @return 修改结果
     */
    int update(Tags tags);

    /**
     * 分页查询标签
     * @param begin 开始位置
     * @param rows 每页记录数
     * @return list
     */
    List<Tags> pageList(int begin, int rows);

    /**
     * 查询标签总记录数
     * @return int
     */
    int Counts();
}
