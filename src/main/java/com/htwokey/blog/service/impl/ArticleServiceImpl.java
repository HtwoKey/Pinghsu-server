package com.htwokey.blog.service.impl;

import com.htwokey.blog.entity.Article;
import com.htwokey.blog.entity.Category;
import com.htwokey.blog.exception.CommonException;
import com.htwokey.blog.mapper.ArticleMapper;
import com.htwokey.blog.mapper.CategoryMapper;
import com.htwokey.blog.mapper.CommentsMapper;
import com.htwokey.blog.mapper.TagsMapper;
import com.htwokey.blog.service.ArticleService;
import com.htwokey.blog.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章service接口实现类
 * @author hch
 * @Date 2019-03-19
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
    private final ArticleMapper articleMapper;
    private final CategoryMapper categoryMapper;
    private final TagsMapper tagsMapper;
    private final CommentsMapper commentsMapper;

    @Autowired
    public ArticleServiceImpl(ArticleMapper articleMapper, CategoryMapper categoryMapper, TagsMapper tagsMapper, CommentsMapper commentsMapper) {
        this.articleMapper = articleMapper;
        this.categoryMapper = categoryMapper;
        this.tagsMapper = tagsMapper;
        this.commentsMapper = commentsMapper;
    }


    @Override
    public int add(Article article, String tags) {

        try {
            //处理分类信息
            Category category = categoryMapper.get(article.getCategory());
            article.setCname(category.getEnglish());
            //过滤html中的emoji表情
            article.setContentHtml(StringUtil.filterEmoji(article.getContentHtml()));
            //保存文章
            int c = articleMapper.add(article);
            //得到自增长的主键id
            int id = article.getId();
            if(c > 0) {
                //添加标签
                addArticleTags(id,tags);
                return 1;
            }
            return 0;
        } catch (Exception e) {
            logger.error("ArticleServiceImpl.add:{}", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public int update(Article article, String tags) {
        try {
            //处理分类信息
            Category category = categoryMapper.get(article.getCategory());
            article.setCname(category.getEnglish());
            //过滤html中的emoji表情
            article.setContentHtml(StringUtil.filterEmoji(article.getContentHtml()));
            //更新文章
            int c = articleMapper.update(article);

            //文章更新成功后才继续更新标签
            if (c > 0){
                //更新文章标签，先删除在添加
                tagsMapper.deleteByAid(article.getId());
                addArticleTags(article.getId(),tags);
                return 1;
            }
            return 0;
        } catch (Exception e) {
            logger.error("ArticleServiceImpl.update:{}", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public List<Article> list(int begin, int rows) {
        try {
            return articleMapper.list(begin,rows);
        } catch (Exception e) {
            logger.error("ArticleServiceImpl.LIST:{}", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public List<Article> articleList(int begin, int rows) {
        try {
            return articleMapper.articleList(begin,rows);
        } catch (Exception e) {
            logger.error("ArticleServiceImpl.ARTICLE_LIST:{}", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public int counts() {
        return articleMapper.counts();
    }

    @Override
    public Article getArticle(int id) {
        try {
            return articleMapper.getArticle(id);
        } catch (Exception e) {
            logger.error("ArticleServiceImpl.getArticle:{}", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }


    @Override
    public List<Article> findByCategory(int id) {
        try {
            return articleMapper.findByCategory(id);
        } catch (Exception e) {
            logger.error("ArticleServiceImpl.findByCategory:{}", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public List<Map<String, Object>> timeGrouping() {
        return articleMapper.timeGrouping();
    }

    @Override
    public List<Map<String, Object>> timeLine() {
        return articleMapper.timeLine();
    }

    @Override
    public List<Map<String, Object>> queryKeyword(String keyword) {
        try {
            return articleMapper.queryKeyword(keyword);
        } catch (Exception e) {
            logger.error("ArticleServiceImpl.queryKeyword:{}", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }

    }

    @Override
    public void hits(int id) {
        try {
            articleMapper.hits(id);
        } catch (Exception e) {
            logger.error("ArticleServiceImpl.hits:{}", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }


    @Override
    public List<Map<String, Object>> tagGrouping(int id) {
        try {
            return articleMapper.tagGrouping(id);
        } catch (Exception e) {
            logger.error("ArticleServiceImpl.tagGrouping:{}", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public int del(int id) {
        try {
            //1.删除文章的标签
            tagsMapper.deleteByAid(id);
            //2.删除文章的所有评论
            commentsMapper.delByAid(id);
            //3.删除文章
            return articleMapper.del(id);
        } catch (Exception e) {
            logger.error("ArticleServiceImpl.del:{}", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public String selectCommentSwitch(int id) {
        try {
            return articleMapper.selectCommentSwitch(id);
        } catch (Exception e) {
            logger.error("ArticleServiceImpl.selectCommentSwitch:{}", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public int changeCommentSwitch(int id, String value) {
        try {
            return articleMapper.changeCommentSwitch(id,value);
        } catch (Exception e) {
            logger.error("ArticleServiceImpl.changeCommentSwitch:{}", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public int changeStatus(int id, String value) {
        try {
            return articleMapper.changeStatus(id,value);
        } catch (Exception e) {
            logger.error("ArticleServiceImpl.changeStatus:{}", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public Map<String,Object> get(int id) {
        try {
            Map<String, Object> map = new HashMap<>(3);
            Article article =  articleMapper.get(id);
            //如果文章存在才去查询文章使用的标签
            if (article != null){
                List<Integer> list = tagsMapper.findArticle_tagsByAid(id);
                map.put("article",article);
                map.put("tags",list);
            }
            return map;
        } catch (Exception e) {
            logger.error("ArticleServiceImpl.get:{}", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public List<Map<String, Object>> next(int id) {
        try {
            return articleMapper.next(id);
        } catch (Exception e) {
            logger.error("ArticleServiceImpl.changeStatus:{}", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    /**
     * 添加标签
     * @param id 文章id
     * @param tags 标签
     */
    private void  addArticleTags(int id,String tags) throws Exception {
        if(StringUtils.isNotEmpty(tags)) {
            //得到所有标签
            String[] ts = StringUtils.split(tags, ",");
            //保存文章标签
            for (String str : ts) {
                if(NumberUtils.isCreatable(str))
                {
                    int tag = Integer.parseInt(str);
                    tagsMapper.addtags(id, tag);
                }
            }
        }
    }

}
