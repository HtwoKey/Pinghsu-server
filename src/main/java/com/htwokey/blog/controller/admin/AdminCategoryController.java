package com.htwokey.blog.controller.admin;

import com.htwokey.blog.controller.BaseController;
import com.htwokey.blog.entity.Category;
import com.htwokey.blog.entity.PageUtil;
import com.htwokey.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类模块-外部数据接口
 * @author hch
 */

@RestController
@RequestMapping("/admin/category")
public class AdminCategoryController extends BaseController {

    private final CategoryService categoryService;

    @Autowired
    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 获取所有分类
     * @return json
     */
    @GetMapping("/list")
    public String list(){

        List<Category> list = categoryService.list();
        return toJsonP(list);
    }

    /**
     * 分页分类列表
     * @param page 当前页数
     * @param rows 每页记录数
     * @return json
     */
    @GetMapping("/page")
    public String pageList(int page,int rows){
        // 返回分页数据， 需要total ：总记录数； rows：分页数据Json格式
        // 开始位置
        int begin = (page - 1) * rows;
        // 1 得到分页的list集合
        List<Category> list = categoryService.pageList(begin,rows);
        // 2 得到总记录数
        int count = categoryService.counts();
        // 3 把list和count构造需要json格式，返回页面
        PageUtil pageBean = new PageUtil(list, page, rows, count);
        return toJsonP(pageBean);
    }

    /**
     * 添加分类
     * @param category 分类对象
     * @return 执行结果
     */
    @PostMapping("/")
    public String add(Category category){

        int c = categoryService.add(category);
        if (c > 0) {
            return run_success();
        }
        return run_false();
    }

    /**
     * 删除分类
     * @param id 分类id
     * @return 执行结果
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id){

        int c = categoryService.delete(id);
        if (c > 0){
            return run_success();
        }
        return run_false("删除失败，请先删除分类下的文章");
    }

    /**
     * 获取分类信息
     * @param id 分类id
     * @return 分类json
     */
    @GetMapping("/{id}")
    public String getCategory(@PathVariable int id){
        Category category = categoryService.get(id);
        if (category != null){
            return toJsonP(category);
        }
        return run_false();
    }


    @PostMapping("/update")
    public String update(Category category){
        int c = categoryService.update(category);
        if (c > 0) {
            return run_success();
        }
        return run_false();
    }
}
