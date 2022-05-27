package com.htwokey.blog.configure;


import com.htwokey.blog.entity.Httplog;
import com.htwokey.blog.service.HttplogService;
import com.htwokey.blog.utils.IpAdrressUtil;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpServletRequest;


/**
 * @author by hchbo
 * @Description 定义一个切面 通过aop 记录所有URl请求
 * @Date 2019/2/19 11:41
 */
@Aspect
@Component
public class UrlLogRecordAspect {

    private static final Logger logger = LoggerFactory.getLogger(UrlLogRecordAspect.class);

    private final HttplogService httplogService;

    @Autowired
    public UrlLogRecordAspect(HttplogService httplogService) {
        this.httplogService = httplogService;
    }

    /**
     * ..表示包及子包 该方法代表controller层的所有方法
     */
    @Pointcut("execution(public * com.htwokey.blog.controller..*.*(..))")
    public void controllerMethod() {}

    /**
     * 打印请求地址和参数
     */
    @Before("controllerMethod()")
    public void logRequestInfo() {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        try {
            assert attributes != null;
            HttpServletRequest request = attributes.getRequest();
            String ip = IpAdrressUtil.getIpAdrress(request);
            String url = request.getRequestURL().toString();
            String method = request.getMethod();
            String uri = request.getRequestURI();
            String queryString = request.getQueryString();
            logger.info("请求开始, url: {}, method: {}, uri: {}, params: {}", url, method, uri, queryString);
            //保存访问后端的请求
            Httplog httplog = new Httplog();
            httplog.setIp(ip);
            httplog.setMethod(method);
            httplog.setUrl(uri);
            httplog.setParams(queryString);
            httplogService.add(httplog);
        }catch (Exception e){
            logger.error("URLLogRecordAspect", e);
        }
    }

    /**
     * 打印响应信息
     */
    @AfterReturning(returning = "object", pointcut = "controllerMethod()")
    public void logResultVoInfo(Object object ){
        logger.info("响应信息如下：");
        logger.info("response：{}",object !=null? object.toString():" ");
    }
}
