package com.htwokey.blog.service;

import java.util.List;
import java.util.Map;

/**
 * @author by hchbo
 * @Classname DatacenterService
 * @Description TODO
 * @Date 2019-05-30 13:47
 */
public interface DatacenterService {

    /**
     * 获取系统的访问量, 即页面浏览量或点击量
     * @return pv
     */
    int getPV();

    /**
     * 获取系统的独立ip数
     * @return ip
     */
    int getIP();

    /**
     * 获取访问量的统计数据
     * @return
     */
    List<Map<String, Object>> getPvChart();

    /**
     * 获取独立ip的统计数据
     * @return
     */
    List<Map<String, Object>> getIpChart();


    /**
     * 用户设备类型占比数据
     * @return
     */
    List<Map<String,Object>> osProportion();
}
