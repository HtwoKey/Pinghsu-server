package com.htwokey.blog.configure;

import com.htwokey.blog.interceptor.CorsInterceptor;
import com.htwokey.blog.interceptor.TokenInterceptor;

import org.jetbrains.annotations.NotNull;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.*;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;


/**
 * @author by hchbo
 * @Description 系统的其他配置
 * @Date 2019/2/21 10:42
 */
@Configuration
public class BlogConfig implements WebMvcConfigurer {

    /**
     * 使用bean注解提前加载拦截器，防止拦截器注入service为空
     */
    @Bean
    public TokenInterceptor getTokenInterceptor(){
        return new TokenInterceptor();
    }
    @Bean
    public CorsInterceptor getCorsInterceptor(){
        return new CorsInterceptor();
    }

    /**
     * 添加拦截器
     * @param registry 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //跨域拦截器
        registry.addInterceptor(getCorsInterceptor()).addPathPatterns("/**");
        //校验token，验证身份
        registry.addInterceptor(getTokenInterceptor()).addPathPatterns("/api/admin/**");
    }


    /**
     * 添加类型转换器和格式化器
     */
    @Override
    public void addFormatters(@NotNull FormatterRegistry registry) {
        //registry.addFormatterForFieldType(LocalDate.class, new USLocalDateFormatter());
    }



    /**
     * 配置消息转换器，解决fastjson在序列化对象时中文出现乱码问题
     *
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        //输出空值字段
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,SerializerFeature.WriteMapNullValue);
        //处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        fastConverter.setFastJsonConfig(fastJsonConfig);

        converters.add(fastConverter);
    }

    /*
      配置文件上传
      @return
     */
//    @Bean
//    public MultipartConfigElement multipartConfigElement(){
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        // 上传临时保存目录
//        factory.setLocation("/temp");
//        // 设置文件上传
//        factory.setMaxFileSize(DataSize.of(5, DataUnit.MEGABYTES));
//        factory.setMaxRequestSize(DataSize.of(5,DataUnit.MEGABYTES));
//
//        return factory.createMultipartConfig();
//    }

}
