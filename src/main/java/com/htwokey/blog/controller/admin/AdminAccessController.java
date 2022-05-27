package com.htwokey.blog.controller.admin;

import com.htwokey.blog.controller.BaseController;
import com.htwokey.blog.entity.Access;
import com.htwokey.blog.entity.PageUtil;
import com.htwokey.blog.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description 用户访问代理记录，后端接口
 * @author hch
 */
@RestController
@RequestMapping("/admin/access")
public class AdminAccessController extends BaseController {

    private final AccessService accessService;

    @Autowired
    public AdminAccessController(AccessService accessService) {
        this.accessService = accessService;
    }

    @GetMapping("/list")
    public String list(int page,int rows){

        // 返回分页数据， 需要total ：总记录数； rows：分页数据Json格式
        // 开始位置
        int begin = (page - 1) * rows;
        // 1 得到分页的list集合
        List<Access> list = accessService.list(begin,rows);
        // 2 得到总记录数
        int count = accessService.counts();
        // 3 把list和count构造需要json格式，返回页面
        PageUtil pageBean = new PageUtil(list, page, rows, count);
        // 返回json格式的数据
        return toJsonP(pageBean);
    }

}
