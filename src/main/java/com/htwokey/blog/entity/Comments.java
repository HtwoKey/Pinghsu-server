package com.htwokey.blog.entity;

import lombok.Data;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

/**
 *
 * @Description 评论实体类
 * @author  hch
 * @Date 2018/8/20 12:04
 * @version 1.0
 **/

@Component
@Data
public class Comments {

    private int id;
    /** 名称*/
    private String name;
    /** 邮箱 */
    private String email;
    /** 个人站点 */
    private String url;
    /** 内容 */
    private String content;
    /** ip地址 */
    private String ip;
    /** 评论时间 */
    private Date time;
    /** 评论文章id */
    private int article;
    /** 回复评论id */
    private int pid;
    /** 评论设备名称 */
    private String osName;
    /** 设备类型 */
    private String osGroup;
    /** 浏览器 */
    private String browser;
    /** 管理员回复标识 */
    private int admin;
    /** 是否阅读标识 */
    private String status;
    /** 友链标识 */
    private int links;
    /**
     * 回复批量
     */
    private List<Comments> children;
}
