package com.htwokey.blog.exception;

import java.util.Map;

/**
 * @author by hchbo
 * @Classname CommonException
 * @Description 自定义异常类
 * @Date 2019/2/19 10:57
 */
public class CommonException extends RuntimeException {

    private boolean status;
    private int code;
    private String message;
    private Map data;

    /**
     * 空的构造函数
     */
    public CommonException(){

    }

    public CommonException(int code,boolean status,String massage, Map data){
        this.code = code;
        this.status = status;
        this.message = massage;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }

}
