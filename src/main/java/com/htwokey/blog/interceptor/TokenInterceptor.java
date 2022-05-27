package com.htwokey.blog.interceptor;

import com.htwokey.blog.entity.User;
import com.htwokey.blog.exception.CommonException;
import com.htwokey.blog.service.UserService;
import com.htwokey.blog.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 检查访问令牌过滤器
 * @author hch
 */

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String url = request.getRequestURI();
        List<String> urlList = new ArrayList<>();
        urlList.add("api/admin/login");
        urlList.add("api/admin/logout");

        //过滤不需要拦截的请求
        for (String method: urlList){
            if (url.contains(method)){
                return true;
            }
        }

        //获取token
        String token = request.getHeader("X-Token");

        if (token == null) {
            response.setStatus(401);
            throw new CommonException(401,false,"你还没有登录，请重新登录",null);
        }
        //验证token是否过期
        if(TokenUtils.checkExpiresAt(new Date(),token)){
            response.setStatus(401);
            throw new CommonException(401,false,"登录超时，请重新登录",null);
        }
        //获取用户id
        int uid = TokenUtils.getUid(token);
        if (uid < 0){
            response.setStatus(401);
            throw new CommonException(401,false,"非法令牌,禁止访问",null);
        }
        // 验证token
        User user = userService.findById(uid);
        if (user == null){
            response.setStatus(401);
            throw new CommonException(401,false,"非法令牌，禁止访问",null);
        }
        if(!user.getToken().equals(token)){
            response.setStatus(401);
            throw new CommonException(401,false,"令牌已失效，请重新登录",null);
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
