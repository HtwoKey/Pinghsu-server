package com.htwokey.blog.controller.admin;

import com.htwokey.blog.controller.BaseController;
import com.htwokey.blog.entity.Comments;
import com.htwokey.blog.entity.PageUtil;
import com.htwokey.blog.service.CommentsService;
import com.htwokey.blog.utils.IpAdrressUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *  管理系统评论模块调用的接口
 * @author hch
 */
@RestController
@RequestMapping("/admin/comments")
public class AdminCommentsController extends BaseController {

    private final CommentsService commentsService;

    @Autowired
    public AdminCommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    /**
     * 评论列表
     */
    @GetMapping("/listPage")
    public String list(int page,int rows){

        // 返回分页数据， 需要total ：总记录数； rows：分页数据Json格式
        // 开始位置
        int begin = (page - 1) * rows;
        // 1 得到分页的list集合
        List<Map<String,Object>> list = commentsService.pageList(begin,rows);
        // 2 得到总记录数
        int count = commentsService.counts();
        // 3 把list和count构造需要json格式，返回页面
        PageUtil pageBean = new PageUtil(list, page, rows, count);
        return toJsonP(pageBean);
    }

    /**
     * 删除评论
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id){

        int c = commentsService.delete(id);
        if ( c > 0) {
            return run_success();
        }
        return run_false();
    }

    /**
     * 后端回复评论
     */
    @PostMapping("/reply")
    public String reply(Comments comments, HttpServletRequest request){

        //获取留言人IP地址
        String ip = IpAdrressUtil.getIpAdrress(request);
        //获取留言人的设备信息
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        //获取浏览器信息
        Browser browser = userAgent.getBrowser();
        //获取用户设备信息
        OperatingSystem os = userAgent.getOperatingSystem();

        comments.setName("作者");
        comments.setEmail("123@qq.com");
        comments.setUrl("https://htwokey.com");
        comments.setAdmin(82);
        comments.setStatus("true");
        comments.setIp(ip);
        comments.setOsName(os.getName());
        comments.setOsGroup(os.getGroup().toString());
        comments.setBrowser(browser.getName());

        int c = commentsService.add(comments);
        if (c > 0) {
            return run_success();
        }
        return run_false();
    }

    /**
     * 修改评论阅读状态
     */
    @GetMapping("/changeStatus/{id}")
    public String changeStatus(@PathVariable int id){
        int c = commentsService.changeStatus(id);
        if (c > 0) {
            return run_success();
        }
        return run_false();
    }

    /**
     * 获取未读评论条数
     */
    @GetMapping("/unreadCount")
    public String unreadCommentsCount(){
        int i = commentsService.unreadCommentsCount();
        return run_success("message",i);
    }
}
