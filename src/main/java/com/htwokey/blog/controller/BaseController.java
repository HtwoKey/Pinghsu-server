package com.htwokey.blog.controller;


import java.util.*;
import com.htwokey.blog.entity.PageUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 *  基础Controller
 * @author hch
 */

public abstract class BaseController {
    protected static final Logger log = LoggerFactory.getLogger(BaseController.class);


    /**
     * JSON 转换
     * @param object 对象
     * @return json
     */

    protected String toJsonP(Object object)
    {
        //设置时区
        JSON.defaultTimeZone = TimeZone.getTimeZone("GMT");
        JSONObject jsonObject = (JSONObject) JSON.toJSON(object);
        jsonObject.put("status", true);
        jsonObject.put("code", 200);
        return JSON.toJSONString(jsonObject,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue);
    }
    protected String toJsonP(List<?> list) {
        if (list == null){
            list = new ArrayList<>();
        }
        JSONArray array = (JSONArray) JSON.toJSON(list);
        return toJsonP(array);
    }

    protected String toJsonP(JSONArray array) {
        JSONObject jsonObject = new JSONObject();
        String rowsName = "data";
        jsonObject.put(rowsName, array);
        return toJsonP(jsonObject);
    }

    protected String toJsonP(PageUtil page ) {
        JSONObject jsonObject = new JSONObject();
        String rowsName = "data";
        jsonObject.put(rowsName, page.getList());
        jsonObject.put("count", page.getTotal());
        jsonObject.put("totalPage",page.getTotalpage());
        jsonObject.put("page",page.getPage());
        jsonObject.put("size",page.getSize());
        return toJsonP(jsonObject);
    }

    /**
     * 错误提示
     * @param message 消息
     * @return json
     */
    protected String run_false(String message) {
        JSONObject obj = new JSONObject();
        obj.put("status", false);
        obj.put("code", 400);
        obj.put("message", message);
        return obj.toJSONString();

    }
    protected String run_false() {
        JSONObject obj = new JSONObject();
        obj.put("status", false);
        obj.put("message", "error");
        obj.put("code", 400);
        return obj.toJSONString();

    }
    /**
     * 正确提示
     * @param message 消息
     * @return json
     */
    protected String run_success(String message) {
        JSONObject obj = new JSONObject();
        obj.put("status", true);
        obj.put("code", 200);
        obj.put("message", message);
        return obj.toJSONString();
    }
    protected String run_success() {
        JSONObject obj = new JSONObject();
        obj.put("status", true);
        obj.put("code", 200);
        obj.put("message", "success");
        return obj.toJSONString();
    }
    protected String run_success(String key ,Object value) {
        JSONObject obj = new JSONObject();
        obj.put("status", true);
        obj.put("code", 200);
        obj.put(key, value);
        return obj.toJSONString();
    }

    /**
     * 空数据提示
     * @return json
     */
    protected String run_nullData() {
        JSONObject obj = new JSONObject();
        obj.put("status", true);
        obj.put("code", 200);
        obj.put("data", null);
        obj.put("message", "空数据");
        return obj.toJSONString();
    }

    /**
     * 未登陆提示
     * @return json
     */
    protected String run_nologin() {
        JSONObject obj = new JSONObject();
        obj.put("status", true);
        obj.put("message", "用户认证失败！");
        obj.put("code", 500);
        return obj.toJSONString();
    }

    /**
     * 参数不正确提示
     * @return json
     */
    protected String run_noparam() {
        JSONObject obj = new JSONObject();
        obj.put("status", false);
        obj.put("data", null);
        obj.put("message", "请求参数不正确！");
        return obj.toJSONString();
    }

    /**
     * 判断参数是否为空！
     *
     * @param args 参数
     * @return 判断结果
     */
    protected boolean isEmpty(Object... args) {

        for (Object string : args) {
            if (string == null) {
                return true;
            }
            if (StringUtils.isEmpty(string.toString())) {
                return true;
            }
        }
        return false;

    }


    /**
     * is函数 判断是否相等
     */
    boolean isSame(Integer ma,Integer mb) {
        if(ma==null) {
            return true;
        }
        int a = ma;
        int b= mb;
        if(a<0) {
            return true;
        }
        return a==b;
    }
    boolean isSame(Double da,Double db) {
        if(da==null) {
            return true;
        }
        double a = da;
        double b = db;
        if(a<0) {
            return true;
        }
        return a==b;
    }
    boolean isSame(Long la,Long lb) {
        if(la==null) {
            return true;
        }
        long a = la;
        long b = lb;
        if(a<0) {
            return true;
        }
        return a==b;
    }
    boolean isSame(String a,String b) {
        if(a==null) {
            return true;
        }
        return a.equals(b);
    }

}
