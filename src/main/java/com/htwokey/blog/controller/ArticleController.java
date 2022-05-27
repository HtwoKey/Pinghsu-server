package com.htwokey.blog.controller;


import com.htwokey.blog.entity.Article;
import com.htwokey.blog.entity.PageUtil;
import com.htwokey.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章接口
 * @author hch
 */

@RestController
@RequestMapping("/article")
public class ArticleController extends BaseController {

    /**
     * 文章列表分页行数
     */
    private static final int ROWS = 12;
    private final ArticleService articleService;

    /**
     * 构造器注入
     */
    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 分页查询接口
     * @param p 页数
     * @return 分页对象
     */
    @GetMapping("/item/{p}")
    public String item(@PathVariable int p) {

        //计算起始位置
        int begin = (p - 1) * ROWS;
        // 获取数据
        List<Article> list = articleService.list(begin, ROWS);
        if (list == null || list.size() == 0) {
            return run_noparam();
        }
        int total = articleService.counts();
        //将数据封装到分页实体中
        PageUtil page = new PageUtil(list, p, ROWS, total);

        // 返回json格式的数据
        return toJsonP(page);
    }

    /**
     * 根据id获取文章接口
     * @param id 文章id
     * @return 文章
     */
    @GetMapping("/{id}")
    public String getArticle ( @PathVariable int id){

        //文章阅读量加1
        articleService.hits(id);
        Article article = articleService.getArticle(id);
        if (article == null) {
            return run_false("参数错误");
        }
        return toJsonP(article);
    }

    /**
     * 文章分类查询接口
     * @return 文章列表
     */
    @GetMapping("/cat/{id}")
    public String findByCategory(@PathVariable int id){

        List<Article> list = articleService.findByCategory(id);
        if (list == null || list.size() == 0) {
            return run_false("没有数据");
        }
        return toJsonP(list);
    }

    /**
     * 文章标签分组查询接口
     * @param id 标签id
     * @return 按标签分组的文章列表
     */
    @GetMapping("/tag/{id}")
    public String findByTags(@PathVariable int id){

        List<Map<String,Object>> list = articleService.tagGrouping(id);
        if (list == null || list.size() == 0) {
            return run_false("查询结果为空");
        }
        return toJsonP(list);
    }


    /**
     * 时间线功能数据接口
     * @return 时间分组和文章列表
     */
    @GetMapping("/timeline")
    public String groupByTime(){
        //时间分组
        List<Map<String,Object>> time = articleService.timeGrouping();
        //文章列表分组
        List<Map<String,Object>> list = articleService.timeLine();

        if (time.size() == 0 || list.size() == 0){
            return run_false();
        }
        // 组装数据
        Map<String, Object> map = new HashMap<>(3);
        map.put("item",list);
        map.put("time",time);
        return toJsonP(map);
    }

    /**
     * 查询上一篇和下一篇文章
     * @param id 文章id
     * @return state
     */
    @GetMapping("/next/{id}")
    public String next(@PathVariable int id){
        if(id > 0){
            return toJsonP(articleService.next(id));
        }
        return run_false();
    }

    /**
     * 文章标题模糊查询接口
     * @param keyword 关键字
     * @return 查询结果
     */
    @GetMapping("/search")
    public String search(String keyword){
        if ("".equals(keyword) || keyword.length() < 1) {
            return run_noparam();
        }
        List<Map<String,Object>> list = articleService.queryKeyword(keyword);
        if (list.size() > 0){
            return toJsonP(list);
        }
        return run_nullData();
    }

}
