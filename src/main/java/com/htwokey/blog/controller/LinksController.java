package com.htwokey.blog.controller;

import com.htwokey.blog.entity.Comments;
import com.htwokey.blog.entity.Links;
import com.htwokey.blog.service.LinksService;
import com.htwokey.blog.utils.IpAdrressUtil;
import com.htwokey.blog.utils.StringUtil;
import com.htwokey.blog.utils.ValidateTools;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Classname LinksController
 * @Description 友链前端数据接口
 * @Date 2019-05-29 11:09
 * @author by hchbo
 */
@RestController
@RequestMapping("/links")
public class LinksController extends BaseController {


    private final LinksService linksService;

    @Autowired
    public LinksController(LinksService linksService) {
        this.linksService = linksService;
    }

    /**
     * 提交友链申请
     * @param comments 评论对象
     * @return
     */
    @PostMapping("/")
    public String add(Comments comments, HttpServletRequest request){

        //获取留言人IP地址
        String ip = IpAdrressUtil.getIpAdrress(request);
        //获取留言人的设备信息
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        //获取浏览器信息
        Browser browser = userAgent.getBrowser();
        //获取用户设备信息
        OperatingSystem os = userAgent.getOperatingSystem();


        String content,content1;
        //检查参数
        if (comments.getName() == null || "".equals(comments.getName())) {
            return run_noparam();
        }
        else if (!ValidateTools.Email(comments.getEmail())) {
            return run_false("参数错误");
        }
        else if (comments.getContent() != null && !"".equals(comments.getContent())){
            // 将留言内容转译并过滤emoji表情
            content = HtmlUtils.htmlEscape(comments.getContent());
            content1 = StringUtil.filterEmoji(content);
        }
        else {
            return run_noparam();
        }

        comments.setIp(ip);
        comments.setContent(content1);
        comments.setOsName(os.getName());
        comments.setOsGroup(os.getGroup().toString());
        comments.setBrowser(browser.getName());
        comments.setAdmin(0);
        comments.setStatus("false");

        int i = linksService.addComments(comments);
        if (i > 0) {
            return run_success();
        }
        return run_false();
    }


    /**
     * 通过申请的友链列表
     * @return
     */
    @GetMapping("/list")
    public String list(){
        List<Links> list = linksService.list();
        return toJsonP(list);
    }

    /**
     * 友链申请列表
     * @return
     */
    @GetMapping("/applyList")
    public String applyList(){
        List<Comments> list = linksService.applyList(0,1000);
        if (list.size() == 0) {
            return run_nullData();
        }
        return toJsonP(list);
    }
}
