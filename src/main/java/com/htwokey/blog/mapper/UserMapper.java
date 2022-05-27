package com.htwokey.blog.mapper;

import com.htwokey.blog.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author hch
 * @date 2019-12-14
 */
@Mapper
public interface UserMapper {

    /**
     * 通过用户账号查询用户
     *
     * @param username 用户账号
     * @return user
     * @throws Exception sql
     */
    @Select("SELECT * FROM blog_user where username = #{username} limit 1 ")
    User login(String username) throws Exception;

    /**
     * 用户登录，存储用户token
     *
     * @param uid   用户id
     * @param token 用户token
     * @throws Exception sql
     */
    @Update("UPDATE blog_user SET token = #{token} WHERE uid = #{uid}")
    void setToken(@Param("uid") int uid, @Param("token") String token) throws Exception;

    /**
     * 累加登录失败次数,并且记录登录时间
     *
     * @param uid 用户id
     * @throws Exception sql
     */
    @Update("UPDATE blog_user SET error = error + 1 ,logged = now() WHERE uid = #{uid}")
    void lockUser(int uid) throws Exception;

    /**
     * 重置登录状态
     *
     * @param uid 用户id
     * @throws Exception sql
     */
    @Update("UPDATE blog_user SET error = 0 WHERE uid = #{uid}")
    void resetLock(int uid) throws Exception;

    /**
     * 通过id查询
     *
     * @param uid 用户id
     * @return User
     * @throws Exception sql
     */
    @Select("SELECT uid,username,name,avatar,introduction,token FROM blog_user where uid = #{uid} limit 1")
    User findById(int uid) throws Exception;

    /**
     * 退出登录,清除用户数据库中的token
     *
     * @param uid 用户id
     * @throws Exception sql
     */
    @Update("UPDATE blog_user SET token = '' WHERE uid = #{uid}")
    void cleanToken(int uid) throws Exception;

    /**
     * 查询所有用户
     *
     * @return list
     */
    @Select("SELECT uid,username,`name`,introduction,avatar,error,`status` FROM `blog_user`")
    List<User> userList();

    /**
     * 添加用户
     * @param user 用户
     * @return 执行结果
     * @throws Exception sql
     */
    @Insert("insert into blog_user(username,password,name,key)values(#{user.username},#{user.password},#{user.name},#{user.key})")
    int add(@Param("user") User user)throws Exception;

    /**
     * 删除用户
     * @param id 用户id
     * @return 执行结果
     * @throws Exception sql
     */
    @Delete("delete from blog_user where uid = #{id}")
    int del(int id)throws Exception;

    /**
     * 修改用户
     * @param user 用户
     * @return 执行结果
     * @throws Exception sql
     */
    @Update("update blog_user set password = #{user.password}, key = #{user.key},name = #{user.name}," +
            "introduction = #{user.introduction},avatar = #{user.avatar} " +
            "where uid = #{user.uid}")
    int update(@Param("user") User user)throws Exception;
}
