package com.htwokey.blog.exception;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;


/**
 * @author by hchbo
 * @Description 自定义全局异常处理
 * @Date 2019/2/19 10:54
 */
@RestController
@RestControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler{

    private Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);


    @Override
    protected @NotNull ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new CommonException(status.value(),false,ex.getMessage(),null), status);
    }


    /**
     * 处理 404，500 等请求错误异常
     * @param e 异常
     * @return 异常消息
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> defaultErrorHandler(Exception e) {
        Map<String, Object> map = new HashMap<>(4);
        logger.error("", e);
        map.put("message",e.getMessage());
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            map.put("code",404);
        }else{
            map.put("code",500);
        }
        map.put("status",false);
        return map;
    }

    /**
     * 自定义异常
     * @param e
     * @return map
     */
    @ExceptionHandler(value = CommonException.class)
    public Map blogException(CommonException e){
        Map<String,Object> map = new HashMap<>(4);
        map.put("message",e.getMessage());
        map.put("code",e.getCode());
        map.put("status",e.isStatus());
        return map;
    }

}
