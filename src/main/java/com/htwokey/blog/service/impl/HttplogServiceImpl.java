package com.htwokey.blog.service.impl;

import com.htwokey.blog.entity.Httplog;
import com.htwokey.blog.exception.CommonException;
import com.htwokey.blog.mapper.HttplogMapper;
import com.htwokey.blog.service.HttplogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname HttplogServiceImpl
 * @Description TODO
 * @Date 2019-06-17 13:48
 * @author by hchbo
 */
@Service
public class HttplogServiceImpl implements HttplogService {

    private static final Logger logger = LoggerFactory.getLogger(HttplogServiceImpl.class);

    @Autowired
    private HttplogMapper httplogMapper;

    @Override
    public int add(Httplog httplog) {
        try {
            return httplogMapper.add(httplog);
        } catch (Exception e) {
            logger.error("HttplogServiceImpl.add", e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Httplog> list(int begin, int rows) {
        try {
            return httplogMapper.list(begin,rows);
        } catch (Exception e) {
            logger.error("HttplogServiceImpl.add", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public int count() {
        return httplogMapper.count();
    }
}
