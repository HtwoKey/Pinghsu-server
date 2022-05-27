package com.htwokey.blog.controller;

import com.htwokey.blog.entity.Access;
import com.htwokey.blog.service.AccessService;
import com.htwokey.blog.utils.IpAdrressUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author by hchbo
 * @Description 用户访问记录
 * @Date 2019/3/27 10:45
 */
@RestController
@RequestMapping("/access")
public class AccessController extends BaseController {


    private final AccessService accessService;

    @Autowired
    public AccessController(AccessService accessService) {
        this.accessService = accessService;
    }


    /**
     * 保存用户访问记录
     * @param request 请求体
     * @return 执行结果
     */
    @GetMapping("/")
    public String add(HttpServletRequest request){
        //获取用户代理
        String userAgentStr = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
        //获取浏览器信息
        Browser browser = userAgent.getBrowser();
        //获取用户设备信息
        OperatingSystem os = userAgent.getOperatingSystem();
        //绑定参数到bean
        Access access = new Access();
        access.setIp(IpAdrressUtil.getIpAdrress(request));
        access.setBrowserManufacturer(browser.getManufacturer().toString());
        access.setBrowserName(browser.getName());
        access.setBrowserType(browser.getBrowserType().toString());
        access.setOsName(os.getName());
        access.setOsGroup(os.getGroup().toString());
        access.setOsDeviceType(os.getDeviceType().toString());
        access.setUserAgent(userAgentStr);
        //添加到数据库
        accessService.addAccess(access);
        return run_success();
    }
}
