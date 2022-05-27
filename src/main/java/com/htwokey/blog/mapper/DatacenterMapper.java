package com.htwokey.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author by hchbo
 * @Classname DatacenterMapper
 * @Description TODO
 * @Date 2019-05-30 13:47
 */
@Mapper
public interface DatacenterMapper {

    /**
     * pv 访问量, 即页面浏览量或点击量
     * @return
     */
    @Select("SELECT COUNT(id) FROM blog_access")
    int pv();

    /**
     * 独立IP数，是指1天内多少个独立的IP浏览了页面
     * @return
     */
    @Select("SELECT COUNT(distinct ip) FROM blog_access")
    int ip();


    /**
     * 查询最近一个月pv访问量图表数据
     * @return
     */
    @Select("SELECT\n" +
            "\tDATE_FORMAT( accessTime, '%Y-%m-%d' ) days,\n" +
            "\tCOUNT( * ) count \n" +
            "FROM\n" +
            "\t(SELECT * FROM blog_access WHERE DATE_SUB( CURDATE(), INTERVAL 30 DAY ) <= accessTime ) \n" +
            "\tAS A \n" +
            "GROUP BY\n" +
            "\tdays;")
    List<Map<String,Object>> pvToMonth();

    /**
     * 查询最近30天的独立ip访问趋势图
     * @return
     */
    @Select("SELECT\n" +
            "\tDATE_FORMAT( accessTime, '%Y-%m-%d' ) days,\n" +
            "\tCOUNT( * ) count \n" +
            "FROM\t\n" +
            "\t(SELECT max(accessTime) accessTime,COUNT(ip) ip FROM blog_access WHERE DATE_SUB( CURDATE(), INTERVAL 30 DAY ) <= accessTime GROUP BY ip)\n" +
            "\tAS A \n" +
            "GROUP BY\n" +
            "\tdays;")
    List<Map<String,Object>> ipToMonth();

    /**
     * 用户设备分组
     * @return
     */
    @Select("SELECT COUNT(osGroup) `value`, MAX(osGroup) `name` FROM blog_access GROUP BY osGroup")
    List<Map<String,Object>> osGroup();
}
