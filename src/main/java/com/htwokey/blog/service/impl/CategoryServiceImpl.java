package com.htwokey.blog.service.impl;

import com.htwokey.blog.entity.Article;
import com.htwokey.blog.entity.Category;
import com.htwokey.blog.exception.CommonException;
import com.htwokey.blog.mapper.ArticleMapper;
import com.htwokey.blog.mapper.CategoryMapper;
import com.htwokey.blog.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类模块-内部数据接口实现类
 * @author hch
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryMapper categoryMapper;
    private final ArticleMapper articleMapper;

    @Autowired
    public CategoryServiceImpl(CategoryMapper categoryMapper, ArticleMapper articleMapper) {
        this.categoryMapper = categoryMapper;
        this.articleMapper = articleMapper;
    }

    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }

    @Override
    public List<Category> pageList(int begin, int rows) {
        try {
            return categoryMapper.pageList(begin,rows);
        } catch (Exception e) {
            logger.error("CategoryServiceImpl.pageList:{}",e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public int counts() {
        return categoryMapper.count();
    }

    @Override
    public int add(Category category) {
        try {
            return categoryMapper.add(category);
        } catch (Exception e) {
            logger.error("CategoryServiceImpl.add:{}",e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public int delete(int id) {
        try {
            //判断该分类下是否有文章
            List<Article> list = articleMapper.findByCategory(id);
            if (list.size() == 0){
                categoryMapper.delete(id);
                return 1;
            }else{
                return 0;
            }
        } catch (Exception e) {
            logger.error("CategoryServiceImpl.delete:{}",e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public int update(Category category) {
        try {
            return categoryMapper.update(category);
        } catch (Exception e) {
            logger.error("CategoryServiceImpl.update:{}",e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public Category get(int id) {
        try {
            return categoryMapper.get(id);
        } catch (Exception e) {
            logger.error("CategoryServiceImpl.get:{}",e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }
}
