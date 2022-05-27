package com.htwokey.blog.controller.admin;


import com.htwokey.blog.controller.BaseController;
import com.htwokey.blog.exception.CommonException;
import com.htwokey.blog.utils.AliYunOssUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * @author by hchbo
 * @Description 上传文件控制器
 * @date 2019/2/19 14:53
 */
@RestController
@RequestMapping("/admin/oss")
public class UploadController extends BaseController {
    private final AliYunOssUtils aliYunOssUtils;

    @Autowired
    public UploadController(AliYunOssUtils aliYunOssUtils) {
        this.aliYunOssUtils = aliYunOssUtils;
    }

    /**
     * 处理上传的文件
     * @param file 文件
     * @return url
     */
    @PostMapping("/")
    public String newUpload(@RequestParam("image") MultipartFile file){
        //判断是否得到文件
        if (file == null){ return run_false();}
        //读取文件名
        String fileName = file.getOriginalFilename();
        //生成新的文件名
        assert fileName != null;
        String newFileName = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));

        //上传到OSS服务器,返回文件MD5摘要
        String tag ;
        try {
            tag = aliYunOssUtils.uploadFile(file.getInputStream(),newFileName);
        } catch (IOException e) {
            throw new CommonException(500,false,e.getMessage(),null);
        }

        //上传成功后生成文件http地址
        if (tag != null){
            String path =  aliYunOssUtils.getImgUrl(newFileName);
            //构造接口返回的数据格式
            Map<String,Object> map = new HashMap<>(2);
            map.put("path",path);
            map.put("fileName",newFileName);
            return toJsonP(map);

        }
        return run_false();
    }

    /**
     * 删除文件接口
     * @param filename 文件名
     * @return 删除结果
     */
    @DeleteMapping("/delImg")
    public String deleteImg(String filename){
        try {
            aliYunOssUtils.deleteFile(filename);
        } catch (Exception e) {
            throw new CommonException(500,false,e.getMessage(),null);
        }
        return null;
    }

}
