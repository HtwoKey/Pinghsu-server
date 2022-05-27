package com.htwokey.blog.controller.admin;

import com.htwokey.blog.controller.BaseController;
import com.htwokey.blog.service.DatacenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Classname DatacenterController
 * @Description 数据中心接口，主要为前端数据分析图形，图表提供数据
 * @Date 2019-05-30 13:44
 * @author by hchbo
 */
@RestController
@RequestMapping("/admin/data")
public class DatacenterController extends BaseController {

    private final DatacenterService datacenterService;

    @Autowired
    public DatacenterController(DatacenterService datacenterService) {
        this.datacenterService = datacenterService;
    }

    /**
     * PV（Page View）访问量, 即页面浏览量或点击量
     * @return
     */
    @GetMapping("/pv")
    public String pageViews(){
        int pv = datacenterService.getPV();
        return run_success("pv",pv);
    }

    /**
     * IP（Internet Protocol）独立IP数，是指1天内多少个独立的IP浏览了页面
     * @return
     */
    @GetMapping("/ip")
    public String internetProtocol(){
        int ip = datacenterService.getIP();
        return run_success("ip",ip);
    }

    /**
     * 访问量图表数据
     * @return
     */
    @GetMapping("/pvChart")
    public String  pvToChart(){
        List<Map<String,Object>> list = datacenterService.getPvChart();
        return toJsonP(list);
    }

    /**
     * 独立ip图表数据
     * @return
     */
    @GetMapping("/ipChart")
    public String ipToChart(){
        List<Map<String,Object>> list = datacenterService.getIpChart();
        return toJsonP(list);
    }

    /**
     * 获取用户设备类型比列
     * @return
     */
    @GetMapping("/osGroup")
    public String osGroup(){
        List<Map<String,Object>> list = datacenterService.osProportion();
        return toJsonP(list);
    }
}
