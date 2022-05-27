package com.htwokey.blog.controller.admin;

import com.htwokey.blog.controller.BaseController;
import com.htwokey.blog.entity.Comments;
import com.htwokey.blog.entity.Links;
import com.htwokey.blog.entity.PageUtil;
import com.htwokey.blog.service.LinksService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
  * @className AdminLinkedController
  * @Description 后端友情连接管理controller
  * @author hchbo
  * @Date 2019-05-299:51
  * @version v1.0
  */
@RestController
@RequestMapping("/admin/links")
public class AdminLinkedController extends BaseController {


    private final LinksService linksService;

    @Autowired
    public AdminLinkedController(LinksService linksService) {
        this.linksService = linksService;
    }


    /**
     * 添加友链
     * @return
     */
    @PostMapping("/")
    public String add(Links links){
        int i = linksService.add(links);
        if(i>0)
            return run_success();
        return run_false();
    }

    /**
     * 删除友链
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id){
        int i = linksService.delete(id);
        if (i>0)
            return run_success();
        return run_false();
    }

    /**
     * 更新友链
     * @return
     */
    @PutMapping("/")
    public String update(Links links){
        int i = linksService.update(links);
        if (i>0)
            return run_success();
        return run_false();
    }

    /**
     * 友链列表分页
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("/pageList")
    public String list(int page,int rows){
        // 返回分页数据， 需要total ：总记录数； rows：分页数据Json格式
        // 开始位置
        int begin = (page - 1) * rows;
        // 1 得到分页的list集合
        List<Links> list = linksService.pageList(begin,rows);
        // 2 得到总记录数
        int count = linksService.Counts();
        // 3 把list和count构造需要json格式，返回页面
        PageUtil pageBean = new PageUtil(list, page, rows, count);
        return toJsonP(pageBean);
    }

    /**
     * 友链申请列表
     * @return
     */
    @GetMapping("/applyList")
    public String applyList(int page,int rows){
        // 返回分页数据， 需要total ：总记录数； rows：分页数据Json格式
        // 开始位置
        int begin = (page - 1) * rows;
        // 1 得到分页的list集合
        List<Comments> list = linksService.applyList(begin,rows);
        // 2 得到总记录数
        int count = linksService.applyListCounts();
        // 3 把list和count构造需要json格式，返回页面
        PageUtil pageBean = new PageUtil(list, page, rows, count);
        return toJsonP(pageBean);
    }


    /**
     * 未处理申请友链的条数
     * @return
     */
    @GetMapping("/untreated")
    public String untreatedLink(){
        int i = linksService.untreatedLinks();
        return run_success("link",i);
    }
}
