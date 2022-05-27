package com.htwokey.blog.controller.admin;

import com.htwokey.blog.controller.BaseController;
import com.htwokey.blog.entity.User;
import com.htwokey.blog.service.UserService;
import com.htwokey.blog.utils.DateUtil;
import com.htwokey.blog.utils.DigestMD;
import com.htwokey.blog.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户模块-外部数据接口
 * @author hch
 */
@CrossOrigin
@RestController
@RequestMapping("/admin")
public class UserController extends BaseController {

    private final UserService userService;

    /**
     * 登录错误次数
     */
    private static final int MAXCOUNT = 5;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 添加新用户
     * @return json
     */
    @PostMapping("/user")
    public String addUser(User user){
        // int i = userService.add(user);
        // if (i > 0)
        //return run_success();
        return run_false("接口已关闭");
    }

    /**
     * 删除用户
     * @param id 用户id
     * @return 执行结果
     */
    @DeleteMapping("/user/{id}")
    public String delUser(@PathVariable int id){

        int i = userService.delete(id);
        if (i > 0) {
            return run_success();
        }
        return run_false();
    }

    /**
     * 修改用户信息
     * @param user 用户信息
     * @return 执行结果
     */
    @PutMapping("/user")
        public String updateUser(User user){
        int i = userService.update(user);
        if (i > 0){
            return run_success();
        }
        return run_false();
    }

    /**
     * 查询所有用户
     * @return 查询结果
     */
    @GetMapping("/user/list")
    public String list(){
        return toJsonP(userService.userList());
    }


    /**
     * 工具token 查询用户信息
     * @param token 用户令牌
     * @return 用户信息
     */
    @GetMapping("/user/info")
    public String info(String token){
        if (token != null && !"".equals(token)){
            int id = TokenUtils.getUid(token);
            User user = userService.findById(id);
            return toJsonP(user);
        }
        return run_noparam();
    }
    /**
     * 登录检查
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    @PostMapping("/login")
    public String login(String username,String password){

        if (username == null || password == null){
            return run_noparam();
        }

        //通过用户名查询用户
        User user = userService.getUser(username);
        // 如果用名存在
        if (user != null) {
            if(user.getStatus() == 1){return run_false("账户已被锁定,请联系管理员");}
            //验证用户密码
            if (user.getPassword().equals(DigestMD.Hmac(password, user.getKey()))) {
                //校验该用户是否可以登录
                if (checkUserLock(user)) {
                    if(user.getError() > 0) {
                        //如果可以登录将错误登录次数置为0
                        userService.resetLock(user.getUid());
                    }
                    // 生成token
                    String token = TokenUtils.createToken(user);
                    // 存储token
                    userService.setToken(token,user.getUid());

                    return run_success("token",token);
                }else {
                    return run_false("输入密码错误次数超过5次，请一个小时后再试");
                }

            } else {
                //检查密码输入错误次数
                if (checkErrNum(user)){
                    return run_false("输入密码错误次数超过5次，请一个小时后再试");
                }else {
                    return run_false("用户名或密码错误");
                }
            }

        }
        return run_false("用户名或密码错误");
    }

    /**
     * 检查用户是否可以登录
     * @param user 用户
     * @return boolean
     */
    private boolean checkUserLock(User user){
        //1.检查错误登录次数是否大于等于5次
        if (user.getError() >= MAXCOUNT){
            //2.检查距离上次登录时间是否有一小时
            LocalDateTime stime = DateUtil.toLocalDateTime(user.getLogged());
            LocalDateTime etime = DateUtil.toLocalDateTime(new Date());
            //计算时差
            Duration duration = DateUtil.between(stime,etime);
            //判断距离上次登录是否超过一小时
            //一小时内不可登录。
            return(duration.toHours() >= 1);
        }
        return true;

    }

    /**
     * 检查密码输入错误次数
     * @param user 用户
     * @return 检查结果
     */
    private boolean checkErrNum(User user){
        if (user.getError() >= MAXCOUNT){
            return true;
        }else {
            //如果输入密码错误次数不超过5次
            //执行登录失败加一的,并记录本次登录时间
            userService.lockUser(user.getUid());
            return false;
        }
    }

    /**
     * 退出登录
     * @return json
     */
    @PostMapping("/logout")
    public String quit(String token){

        int uid = TokenUtils.getUid(token);
        User user = userService.findById(uid);
        if (user != null){
            userService.cleanToken(uid);
            run_success("退出成功");
        }
        return run_success("你已通过验证");
    }
}
