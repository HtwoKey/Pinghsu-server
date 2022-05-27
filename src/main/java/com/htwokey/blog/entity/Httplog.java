package com.htwokey.blog.entity;

import java.util.Date;

/**
 * @Classname Httplog
 * @Description 记录HTTP请求
 * @Date 2019-06-05 11:57
 * @author by hchbo
 */

public class Httplog {
    private int id; //主键，自增长
    private Date time; //请求时间
    private String ip; //请求ip
    private String url; //请求url
    private String method; //请求方法
    private String params; //请求参数

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
