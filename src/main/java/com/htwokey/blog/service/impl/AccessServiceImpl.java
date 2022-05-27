package com.htwokey.blog.service.impl;

import com.htwokey.blog.entity.Access;
import com.htwokey.blog.exception.CommonException;
import com.htwokey.blog.mapper.AccessMapper;
import com.htwokey.blog.service.AccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 用户访问记录接口实现类
 * @author hchbo
 */
@Service
public class AccessServiceImpl implements AccessService {

    private final Logger logger = LoggerFactory.getLogger(AccessServiceImpl.class);

    private final AccessMapper accessMapper;

    @Autowired
    public AccessServiceImpl(AccessMapper accessMapper) {
        this.accessMapper = accessMapper;
    }

    @Override
    public void addAccess(Access access) {
        try {
            accessMapper.add(access);
        } catch (Exception e) {
            // 只记录日志不返回结果
            logger.error("AccessServiceImpl.addAccess", e);
        }
    }

    @Override
    public List<Access> list(int begin, int rows) {
        try {
            return accessMapper.list(begin,rows);
        } catch (Exception e) {
            logger.error("AccessServiceImpl.list:{}", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }

    @Override
    public int counts() {
        try {
            return accessMapper.Counts();
        } catch (Exception e) {
            logger.error("AccessServiceImpl.Counts:{}", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
    }
}
