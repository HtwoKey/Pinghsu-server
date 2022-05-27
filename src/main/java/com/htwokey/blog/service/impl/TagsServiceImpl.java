package com.htwokey.blog.service.impl;

import com.htwokey.blog.entity.Tags;
import com.htwokey.blog.exception.CommonException;
import com.htwokey.blog.mapper.TagsMapper;
import com.htwokey.blog.service.TagsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class TagsServiceImpl implements TagsService {

    private final Logger logger = LoggerFactory.getLogger(TagsServiceImpl.class);


    @Autowired
    private TagsMapper tagsMapper;


    @Override
    public List<Tags> list() {
        try {
            return tagsMapper.list();
        } catch (Exception e) {
            logger.error("TagsServiceImpl.list", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public List<Tags> findByArticleId(int id) {
        try {

            return tagsMapper.findByArticleId(id);
        } catch (Exception e) {
            logger.error("TagsServiceImpl.findByArticleId", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public int addTags(Tags tags) {
        try {
            return tagsMapper.addTags(tags);
        } catch (Exception e) {
            logger.error("TagsServiceImpl.addTags", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public int delTags(int id) {
        try {

            //TODO 删除文章的引用
            tagsMapper.deleteByTid(id);

            return tagsMapper.delTags(id);
        } catch (Exception e) {
            logger.error("TagsServiceImpl.delTags", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }


    @Override
    public List<Map<String, Object>> tagGroup() {
        try {
            return tagsMapper.findTagQuote();
        } catch (Exception e) {
            logger.error("TagsServiceImpl.tagGroup", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public int update(Tags tags) {
        try {
            return tagsMapper.update(tags);
        } catch (Exception e) {
            logger.error("TagsServiceImpl.update", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }


    @Override
    public List<Tags> pageList(int begin, int rows) {
        try {
            return tagsMapper.pageList(begin,rows);
        } catch (Exception e) {
            logger.error("TagsServiceImpl.pageList", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public int Counts() {
        return tagsMapper.Counts();
    }
}
