package com.htwokey.blog.entity;

import java.util.Date;

/**
 * @author hchbo
 * @projectName BlogServer
 * @description: 友情链接实体类
 * @date 2019-04-2513:37
 * @version: v1.0
 */
public class Links {

    private int id;
    private String name;
    private String url;
    private Date time;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
