package com.htwokey.blog.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.htwokey.blog.exception.CommonException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;


/**
  * @Description  阿里云oss图片上传工具
  * @author hchbo
  * @Date 2019-05-16 09:39
  * @version v1.0
  */

@Component
public class AliYunOssUtils {
    private final Logger logger = LoggerFactory.getLogger(AliYunOssUtils.class);

    @Value("${oss.endpoint}")
    private  String endpoint;
    @Value("${oss.accessKeyId}")
    private  String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private  String accessKeySecret;
    @Value("${oss.bucketName}")
    private  String bucketName;
    @Value("${oss.fileHost}")
    private  String fileHost;


    private OSSClient ossClient;

    private OSSClient getOssClient() {
         return  new OSSClient(endpoint,accessKeyId,accessKeySecret);
    }

    /**
     * 上传图片,以输入流方式
     * @param inputStream 文件流
     * @param fileName 文件名
     * @return 返回文件MD5签名
     */
    public String uploadFile(InputStream inputStream, String fileName) {
        String ret;
        ossClient = getOssClient();

        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            //设置上传文件的大小限制
            objectMetadata.setContentLength(inputStream.available());
            //设置缓存
            objectMetadata.setCacheControl("no-cache");
            //设置上传header
            objectMetadata.setHeader("Pragma", "no-cache");
            //设置上传文件类型,
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            //
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
            PutObjectResult putResult = ossClient.putObject(bucketName, fileHost + fileName, inputStream, objectMetadata);
            // 设置文件为公共读
            ossClient.setObjectAcl(bucketName,fileName, CannedAccessControlList.PublicRead);
            ret = putResult.getETag();
        }
        catch (IOException e) {
            logger.error("AliYun Oss Utils upload File:{}", e.getMessage());
            throw new CommonException(500,false,e.getMessage(),null);
        }
        finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                    //关闭链接
                    ossClient.shutdown();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;

    }


    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param fileNameExtension 文件后缀
     * @return String
     */
    private String getcontentType(String fileNameExtension) {
        if (".bmp".equalsIgnoreCase(fileNameExtension))
        {
            return "image/bmp";
        } if (".gif".equalsIgnoreCase(fileNameExtension)) {
            return "image/gif";
        } if (".jpeg".equalsIgnoreCase(fileNameExtension) ||
                ".jpg".equalsIgnoreCase(fileNameExtension) ||
                ".png".equalsIgnoreCase(fileNameExtension)) {
            return "image/jpeg";
        } if (".html".equalsIgnoreCase(fileNameExtension)) {
            return "text/html";
        } if (".txt".equalsIgnoreCase(fileNameExtension)) {
            return "text/plain";
        } if (".vsd".equalsIgnoreCase(fileNameExtension)) {
            return "application/vnd.visio";
        } if (".pptx".equalsIgnoreCase(fileNameExtension) ||
                ".ppt".equalsIgnoreCase(fileNameExtension)) {
            return "application/vnd.ms-powerpoint";
        } if (".docx".equalsIgnoreCase(fileNameExtension) ||
                ".doc".equalsIgnoreCase(fileNameExtension)) {
            return "application/msword";
        } if (".xml".equalsIgnoreCase(fileNameExtension)) {
            return "text/xml";
        }
        return "image/jpeg";
    }


    /**
     * 获得图片路径
     * @param fileName 文件名
     */
    public String getImgUrl(String fileName) {
        if (!StringUtils.isEmpty(fileName)) {
            return getUrl(fileHost + fileName);
        }
        return null;
    }

    /**
     * 获得url链接
     * @param key 文件名
     * @return url
     */
    private String getUrl(String key) {

        ossClient = getOssClient();
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        ossClient.shutdown();
        if (url != null) {
            return url.toString();
        }
        ossClient.shutdown();
        return null;
    }


    /**
     * 删除oss上的文件
     * @param fileName 文件名
     */
    public void deleteFile(String fileName){

        ossClient = getOssClient();
        ossClient.deleteObject(bucketName, fileName);
        ossClient.shutdown();
    }

}
