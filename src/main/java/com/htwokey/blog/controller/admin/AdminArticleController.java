package com.htwokey.blog.controller.admin;

import com.htwokey.blog.controller.BaseController;
import com.htwokey.blog.entity.Article;
import com.htwokey.blog.entity.PageUtil;
import com.htwokey.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 文章后端管理接口
 * @author hch
 */

@RestController
@RequestMapping("/admin/article")
public class AdminArticleController extends BaseController {


    private final ArticleService articleService;
    @Autowired
    public AdminArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }


    /**
     * 文章列表
     * @param page 当前页
     * @param rows 每页记录数
     * @return json
     */
    @GetMapping("/list")
    public String list(int page,int rows){
        // 返回分页数据， 需要total ：总记录数； rows：分页数据Json格式
        // 开始位置
        int begin = (page - 1) * rows;
        // 1 得到分页的list集合
        List<Article> list = articleService.articleList(begin,rows);
        // 2 得到总记录数
        int count = articleService.counts();
        // 3 把list和count构造需要json格式，返回页面
        PageUtil pageBean = new PageUtil(list, page, rows, count);
        // 返回json格式的数据
        return toJsonP(pageBean);
    }


    /**
     * 添加文章
     * @param article 文章
     * @param tags 标签
     * @return 执行结果json
     */
    @PostMapping("/")
    public String add(Article article, @RequestParam("tags") String tags){
        int c = articleService.add(article,tags);
        if (c > 0){
            return run_success();
        }
        return run_false();
    }

    /**
     * 删除文章接口
     * @param id 文章id
     * @return 执行结构json
     */
    @DeleteMapping("/{id}")
    public String del(@PathVariable int id){

        int c = articleService.del(id);
        if (c > 0 ){
            return run_success();
        }
        return run_false();
    }

    /**
     * 编辑文章接口
     * @param article 文章json
     * @return 执行结果
     */
    @PostMapping("/update")
    public String update(Article article,@RequestParam("tags") String tags){

        int c = articleService.update(article,tags);
        if (c > 0){
            return run_success();
        }
        return run_false();
    }

    /**
     * 修改文章的评论开关
     * @param id 文章id
     * @param value 开关值
     * @return 执行结果
     */
    @PostMapping("/changeSwitch")
    public String changeCommentSwitch(int id,String value){
        int c = articleService.changeCommentSwitch(id,value);
        if (c > 0){
            return run_success();
        }
        return run_false();
    }

    /**
     * 修改文章发布状态
     * @param id 文章id
     * @param value 状态值
     * @return 执行结果
     */
    @PostMapping("/changeStatus")
    public String changeStatus(int id,String value){
        int c = articleService.changeStatus(id,value);
        if (c > 0){
            return run_success();
        }
        return run_false();
    }

    /**
     * 后端编辑文章数据回显查询接口
     * @param id 文章id
     * @return map
     */
    @GetMapping("/{id}")
    public String getArticle(@PathVariable  int id){
        // 获取文章
        Map<String,Object> map = articleService.get(id);
        if (map != null){
            return toJsonP(map);
        }
        return run_false();
    }
}
