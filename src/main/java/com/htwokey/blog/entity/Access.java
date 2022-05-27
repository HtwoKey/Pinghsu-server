package com.htwokey.blog.entity;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName Access
 * @Description 访问记录
 * @Author hchbo
 * @Date 2018/12/10 11:28
 * @Version 1.0
 **/

@Component
public class Access {

    private int id;
    /**
     *  ip地址
     */
    private String ip;
    /**
     * 访问时间
     */
    private Date accessTime;
    /**
     * 浏览器名称
     */
    private String browserName;
    /**
     * 浏览器类型
     */
    private String browserType;
    /**
     * 浏览器厂商
     */
    private String browserManufacturer;
    /**
     * 系统名称
     */
    private String osName;
    /**
     * 系统类型
     */
    private String osGroup;
    /**
     * 设备类型
     */
    private String osDeviceType;
    /**
     * 完整的用户代理
     */
    private String userAgent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public String getBrowserManufacturer() {
        return browserManufacturer;
    }

    public void setBrowserManufacturer(String browserManufacturer) {
        this.browserManufacturer = browserManufacturer;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsGroup() {
        return osGroup;
    }

    public void setOsGroup(String osGroup) {
        this.osGroup = osGroup;
    }

    public String getOsDeviceType() {
        return osDeviceType;
    }

    public void setOsDeviceType(String osDeviceType) {
        this.osDeviceType = osDeviceType;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
