package com.htwokey.blog.service.impl;

import com.htwokey.blog.entity.Comments;
import com.htwokey.blog.entity.Links;
import com.htwokey.blog.exception.CommonException;
import com.htwokey.blog.mapper.CommentsMapper;
import com.htwokey.blog.mapper.LinksMapper;
import com.htwokey.blog.service.LinksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
  * @ClassName LinksServiceImpl
  * @Description TODO
  * @author hchbo
  * @Date 2019-05-2910:09
  * @version v1.0
  */

@Service
public class LinksServiceImpl implements LinksService {

    private Logger logger = LoggerFactory.getLogger(LinksServiceImpl.class);

    @Autowired
    private LinksMapper linksMapper;
    @Autowired
    private CommentsMapper commentsMapper;


    @Override
    public int Counts() {
        return linksMapper.Counts();
    }

    @Override
    public List<Links> pageList(int begin, int rows) {
        try {
            return linksMapper.pageList(begin,rows);
        } catch (Exception e) {
            logger.error("LinksServiceImpl.pageList", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public int add(Links links) {
        try {
            return linksMapper.add(links);
        } catch (Exception e) {
            logger.error("LinksServiceImpl.add", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public int delete(int id) {
        try {
            return linksMapper.delete(id);
        } catch (Exception e) {
            logger.error("LinksServiceImpl.delete",e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public int update(Links links) {
        try {
            return linksMapper.update(links);
        } catch (Exception e) {
            logger.error("LinksServiceImpl.update",e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public int addComments(Comments comments) {
        try {
            //添加申请标识
            comments.setLinks(1);
            return commentsMapper.add(comments);
        } catch (Exception e) {
            logger.error("LinksServiceImpl.addComments",e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public List<Links> list() {
        return linksMapper.list();
    }

    @Override
    public List<Comments> applyList(int begin,int rows) {
        try {
            return commentsMapper.applyList(begin,rows);
        } catch (Exception e) {
            logger.error("LinksServiceImpl.applyList",e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public int applyListCounts() {
        return commentsMapper.applyListCounts();
    }

    @Override
    public int untreatedLinks() {
        return commentsMapper.unreadLinkApplication();
    }
}
