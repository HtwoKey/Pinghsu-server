package com.htwokey.blog.controller;


import com.htwokey.blog.entity.Tags;
import com.htwokey.blog.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author hch
 */

@RestController
@RequestMapping("/tags")
public class TagsController extends BaseController{


    private final TagsService tagsService;

    @Autowired
    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    /**
     * 查询文章使用到的标签
     * @param id 文章id
     * @return 文章引用的标签
     */
    @GetMapping("/article/{id}")
    public String articleTags(@PathVariable int id){

        List<Tags> list = tagsService.findByArticleId(id);
        if (list.size() == 0) {
            return run_nullData();
        }
        return toJsonP(list);
    }

    /**
     * 获取所有的标签接口
     * @return json
     */
    @GetMapping("/list")
    public String getAll(){
        List<Tags> list = tagsService.list();
        if (list.size() == 0) {
            return run_nullData();
        }
        return toJsonP(list);
    }

    /**
     * 按标签引用次数分组
     * @return json
     */
    @GetMapping("/group")
    public String citationsByGroup(){
        List<Map<String,Object>> list = tagsService.tagGroup();
        if (list.size() == 0) {
            return run_nullData();
        }
        return toJsonP(list);
    }
}
