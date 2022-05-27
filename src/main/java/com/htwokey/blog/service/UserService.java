package com.htwokey.blog.service;

import com.htwokey.blog.entity.User;
import java.util.List;

/**
 * @author hchbo
 * @Date 2018-10-10
 */
public interface UserService {


    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return user
     */
    User getUser (String username);

    /**
     * 用户登录，存储用户token
     * @param token 用户token
     * @param uid 用户id
     */
    void setToken(String token,int uid);

    /**
     * 累加用户登录失败次数，并记录登录时间
     * @param uid 用户名
     */
    void lockUser(int uid);

    /**
     * 重置登录错误次数
     * @param uid 用户id
     */
    void resetLock(int uid);

    /**
     * 根据用户id查询用户
     * @param uid 用户id
     * @return user
     */
    User findById(int uid);

    /**
     * 退出登录，清除用户在数据库中存储的token
     * @param uid 用户id
     */
    void cleanToken(int uid);


    /**
     * 查询所有的用户
     * @return list
     */
    List<User> userList();

    /**
     * 删除用户
     * @param id 用户id
     * @return 执行结果
     */
    int delete(int id);

    /**
     * 添加新用户
     * @param user 用户信息
     * @return 执行结果
     */
    int add(User user);

    /**
     * 修改用户信息
     * @param user 用户信息
     * @return 修改结果
     */
    int update(User user);
}
