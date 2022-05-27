package com.htwokey.blog.service.impl;

import com.htwokey.blog.mapper.DatacenterMapper;
import com.htwokey.blog.service.DatacenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Classname DatacenterServiceImpl
 * @Description TODO
 * @Date 2019-05-30 13:49
 * @author by hchbo
 */
@Service
public class DatacenterServiceImpl implements DatacenterService {

    @Autowired
    private DatacenterMapper datacenterMapper;


    @Override
    public int getPV() {
        return datacenterMapper.pv();
    }

    @Override
    public int getIP() {
        return datacenterMapper.ip();
    }

    @Override
    public List<Map<String, Object>> getPvChart() {
        return datacenterMapper.pvToMonth();
    }

    @Override
    public List<Map<String, Object>> getIpChart() {
        return datacenterMapper.ipToMonth();
    }

    @Override
    public List<Map<String, Object>> osProportion() {
        return datacenterMapper.osGroup();
    }
}
