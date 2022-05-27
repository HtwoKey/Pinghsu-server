package com.htwokey.blog.controller;

import com.htwokey.blog.entity.Comments;
import com.htwokey.blog.service.ArticleService;
import com.htwokey.blog.service.CommentsService;
import com.htwokey.blog.utils.IpAdrressUtil;
import com.htwokey.blog.utils.StringUtil;
import com.htwokey.blog.utils.ValidateTools;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户评论接口
 * @author hch
 */
@RestController
@RequestMapping("/comments")
public class CommentsController extends BaseController {

    private final CommentsService commentsService;
    private final ArticleService articleService;

    @Autowired
    public CommentsController(CommentsService commentsService, ArticleService articleService) {
        this.commentsService = commentsService;
        this.articleService = articleService;
    }

    /**
     * 发表、回复评论
     */
    @PostMapping("/")
    public String addComments(Comments comments, HttpServletRequest request){

        //检查文章是否开启评论接口
        String status = articleService.selectCommentSwitch(comments.getArticle());
        boolean b = Boolean.parseBoolean(status);
        if (!b){
            return run_false("已关闭评论");
        }

        //获取留言人IP地址
        String ip = IpAdrressUtil.getIpAdrress(request);
        //获取留言人的设备信息
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        //获取浏览器信息
        Browser browser = userAgent.getBrowser();
        //获取用户设备信息
        OperatingSystem os = userAgent.getOperatingSystem();


        String content;
        //检查参数
        if (comments.getName() == null || "".equals(comments.getName())){
            return run_noparam();
        }
        else if (!ValidateTools.Email(comments.getEmail())) {
            return run_false("参数错误");
        }
        else if (comments.getArticle() < 0) {
            return run_noparam();
        }
        else if (comments.getContent() != null && ! "".equals(comments.getContent())){
            // 将留言内容转译并过滤emoji表情
            content = HtmlUtils.htmlEscape(comments.getContent());
            content = StringUtil.filterEmoji(content);
        }

        else {
            return run_noparam();
        }
        // 设置其他参数
        comments.setIp(ip);
        comments.setContent(content);
        comments.setOsName(os.getName());
        comments.setOsGroup(os.getGroup().toString());
        comments.setBrowser(browser.getName());
        comments.setAdmin(0);
        comments.setStatus("false");

        // 添加到数据库
        int n = commentsService.add(comments);
        // 判断是否添加成功
        if (n > 0) {
            return run_success();
        }
        return run_false();
    }

    /**
     * 分页评论列表
     */
    @GetMapping("/{id}")
    public String list(@PathVariable int id){

        List<Comments> list = commentsService.getArticleComments(id);
        if (list.size() == 0){
            return run_nullData();
        }
        return toJsonP(list);
    }


}
