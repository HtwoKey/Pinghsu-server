package com.htwokey.blog.service.impl;

import com.htwokey.blog.entity.Comments;
import com.htwokey.blog.exception.CommonException;
import com.htwokey.blog.mapper.ArticleMapper;
import com.htwokey.blog.mapper.CommentsMapper;
import com.htwokey.blog.service.CommentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author hchbo
 * @date 2019/04/15
 */
@Service
public class CommentsServiceImpl implements CommentsService {

    private final Logger logger = LoggerFactory.getLogger(CommentsServiceImpl.class);

    private final CommentsMapper commentsMapper;
    private final ArticleMapper articleMapper;

    @Autowired
    public CommentsServiceImpl(ArticleMapper articleMapper, CommentsMapper commentsMapper) {
        this.articleMapper = articleMapper;
        this.commentsMapper = commentsMapper;
    }

    @Override
    public List<Map<String,Object>> pageList(int begin, int rows) {
        try {
            return commentsMapper.pageList(begin,rows);
        } catch (Exception e) {
            logger.error("CommentsServiceImpl.pageList:{}", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public List<Comments> getArticleComments(int id) {

        try {
            return commentsMapper.lists(id);
        } catch (Exception e) {
            logger.error("CommentsServiceImpl.getArticleComments:{}", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }


    @Override
    public int add(Comments comments) {
        try {
            int c =  commentsMapper.add(comments);
            if (c > 0){
                //文章的评论数加1
                articleMapper.addComments(comments.getArticle());
                return c;
            }
            return 0;
        } catch (Exception e) {
            //记录日志，并返回结果
            logger.error("CommentsServiceImpl.add:{}", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }

    }

    @Override
    public int counts() {
        return commentsMapper.Counts();
    }

    @Override
    public int delete(int id) {
        try {
            return commentsMapper.delete(id);
        } catch (Exception e) {
            logger.error("CommentsServiceImpl.delete:{}", e.getMessage());
            throw new CommonException(500, false, e.getMessage(), null);
        }
    }

    @Override
    public int changeStatus(int id) {
        try {
            return commentsMapper.changeStatus(id);
        } catch (Exception e) {
            logger.error("CommentsServiceImpl.changeStatus:{}", e.getMessage());
            throw new CommonException(500, false, e.getMessage(), null);
        }
    }

    @Override
    public int unreadCommentsCount() {
        return commentsMapper.unreadMessages();
    }
}
