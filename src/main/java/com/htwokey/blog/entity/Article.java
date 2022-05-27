package com.htwokey.blog.entity;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 文章信息实体类
 * @author hch
 */
@Component
public class Article {
    private int id;
    private String title;
    private String slug;
    private Date created;
    private Date modified;
    private String contentHtml;
    private String contentMkd;
    private String imageCover;
    private int hits;
    private int category;
    private String cname;
    private String status;
    private int commentsnum;
    private String commentSwitch;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public String getContentMkd() {
        return contentMkd;
    }

    public void setContentMkd(String contentMkd) {
        this.contentMkd = contentMkd;
    }

    public String getImageCover() {
        return imageCover;
    }

    public void setImageCover(String imageCover) {
        this.imageCover = imageCover;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCommentsnum() {
        return commentsnum;
    }

    public void setCommentsnum(int commentsnum) {
        this.commentsnum = commentsnum;
    }

    public String getCommentSwitch() {
        return commentSwitch;
    }

    public void setCommentSwitch(String commentSwitch) {
        this.commentSwitch = commentSwitch;
    }
}
