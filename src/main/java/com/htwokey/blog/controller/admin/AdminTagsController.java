package com.htwokey.blog.controller.admin;

import com.htwokey.blog.controller.BaseController;
import com.htwokey.blog.entity.PageUtil;
import com.htwokey.blog.entity.Tags;
import com.htwokey.blog.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author hch
 * @date
 */
@RestController
@RequestMapping("/admin/tags")
public class AdminTagsController extends BaseController {

    private final TagsService tagsService;

    @Autowired
    public AdminTagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    /**
     * 添加标签接口
     * @param tags 标签
     * @return json
     */
    @PostMapping("/")
    public String addTags(Tags tags){
        // 判断参数是否为空
        if (tags.getName() == null || tags.getName().equals(""))
            return run_false("参数为空");

        int count = tagsService.addTags(tags);
        if (count > 0){
            return run_success();
        }
        return run_false();
    }

    /**
     * 删除标签接口
     * @param id 标签id
     * @return json
     */
    @DeleteMapping("/{id}")
    public String delTags(@PathVariable int id){

        int count = tagsService.delTags(id);
        if (count > 0)
            return run_success();
        return run_noparam();

    }

    @PutMapping("/")
    public String update(Tags tags){
        int c = tagsService.update(tags);
        if (c > 0){
            return  run_success();
        }
        return run_false();
    }



    /**
     * 标签列表接口,分页
     * @return json
     */
    @GetMapping("/pageList")
    public String list(int page,int rows){
        // 返回分页数据， 需要total ：总记录数； rows：分页数据Json格式
        // 开始位置
        int begin = (page - 1) * rows;
        // 1 得到分页的list集合
        List<Tags> list = tagsService.pageList(begin,rows);
        // 2 得到总记录数
        int count = tagsService.Counts();
        // 3 把list和count构造需要json格式，返回页面
        PageUtil pageBean = new PageUtil(list, page, rows, count);
        return toJsonP(pageBean);

    }

    /**
     * 无分页标签接口
     * @return
     */
    @GetMapping("/list")
    public String list(){
        List<Tags> list = tagsService.list();
        return toJsonP(list);
    }
}
