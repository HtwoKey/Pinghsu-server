package com.htwokey.blog.entity;

import org.springframework.stereotype.Component;

@Component
public class Article_tags {
    private int id;
    private int tid;//标签id
    private int aid;//文章id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }
}
