package com.htwokey.blog.controller.admin;

import com.htwokey.blog.controller.BaseController;
import com.htwokey.blog.entity.Httplog;
import com.htwokey.blog.entity.PageUtil;
import com.htwokey.blog.service.HttplogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description 请求记录url
 * @Date 2019-06-05 12:04
 * @author by hchbo
 */

@RestController
@RequestMapping("/admin/http")
public class HttpLogController extends BaseController {

    private final HttplogService httplogService;

    @Autowired
    public HttpLogController(HttplogService httplogService) {
        this.httplogService = httplogService;
    }

    @GetMapping("/list")
    public String list(int page,int rows){
        // 返回分页数据， 需要total ：总记录数； rows：分页数据Json格式
        // 开始位置
        int begin = (page - 1) * rows;
        // 1 得到分页的list集合
        List<Httplog> list = httplogService.list(begin,rows);
        // 2 得到总记录数
        int count = httplogService.count();
        // 3 把list和count构造需要json格式，返回页面
        PageUtil pageBean = new PageUtil(list, page, rows, count);
        // 返回json格式的数据
        return toJsonP(pageBean);
    }

}
