package com.htwokey.blog.service.impl;

import com.htwokey.blog.entity.User;
import com.htwokey.blog.exception.CommonException;
import com.htwokey.blog.mapper.UserMapper;
import com.htwokey.blog.service.UserService;
import com.htwokey.blog.utils.DigestMD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * @author hchbo
 * @date 2018-10-10
 */
@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserMapper userMapper;
    @Autowired
    public UserServiceImpl(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @Override
    public User getUser(String username) {
        try {
            return userMapper.login(username);
        } catch (Exception e) {
            logger.error("UserServiceImpl.getUser", e);
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public void lockUser(int uid) {
        try {
            userMapper.lockUser(uid);
        } catch (Exception e) {
            logger.error("UserServiceImpl.lockUser", e);
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public void resetLock(int uid) {
        try {
            userMapper.resetLock(uid);
        } catch (Exception e) {
            logger.error("UserServiceImpl.resetLock", e);
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public User findById(int uid) {
        try {
            return userMapper.findById(uid);
        } catch (Exception e) {
            logger.error("UserServiceImpl.findById", e);
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public void cleanToken(int uid) {
        try {
            userMapper.cleanToken(uid);
        } catch (Exception e) {
            logger.error("UserServiceImpl.cleanToken", e);
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public void setToken(String token, int uid) {
        try {
            userMapper.setToken(uid,token);
        } catch (Exception e) {
            logger.error("UserServiceImpl.setToken", e);
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public List<User> userList() {
        return userMapper.userList();
    }


    @Override
    public int delete(int id) {
        try {
            return userMapper.del(id);
        } catch (Exception e) {
            logger.error("UserServiceImpl.delete", e);
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }


    @Override
    public int add(User user) {
        try {
            // 生成随机key
            String key = String.valueOf(new Random().nextInt(899999)+100000);
            // 加密密码
            String pwd = DigestMD.Hmac(user.getPassword(),key);
            user.setPassword(pwd);
            user.setKey(key);
            //添加用户到数据库
            return userMapper.add(user);
        } catch (Exception e) {
            logger.error("UserServiceImpl.add", e);
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public int update(User user) {
        try {
            // 生成随机key
            String key = String.valueOf(new Random().nextInt(899999)+100000);
            // 加密密码
            String pwd = DigestMD.Hmac(user.getPassword(),key);

            user.setPassword(pwd);
            user.setKey(key);
            return userMapper.update(user);
        } catch (Exception e) {
            logger.error("UserServiceImpl.add", e);
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }
}
