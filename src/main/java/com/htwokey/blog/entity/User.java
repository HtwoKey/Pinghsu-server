package com.htwokey.blog.entity;


import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 * @author hch
 */
public class User implements Serializable {

    /**
     * 主键id
     */
    private int uid;
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户简介
     */
    private String introduction;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 主页地址
     */
    private String homeUrl;
    /**
     * 用户名，昵称
     */
    private String name;
    /**
     * 登录错误次数
     */
    private int error;
    /**
     * 上传登录时间
     */
    private Date logged;
    /**
     * 登录密钥
     */
    private String key;
    /**
     * 账户状态
     */
    private int status;
    /**
     * 登录令牌
     */
    private String token;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLogged() {
        return logged;
    }

    public void setLogged(Date logged) {
        this.logged = logged;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
