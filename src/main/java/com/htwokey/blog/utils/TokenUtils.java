package com.htwokey.blog.utils;

import com.htwokey.blog.entity.User;
import com.htwokey.blog.exception.CommonException;
import io.jsonwebtoken.*;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author by hchbo
 * @Description token令牌生成工具
 * @Date 2019/2/20 10:25
 */
public class TokenUtils {


    /**
     * 签名密钥
     */
    private static final String SECRET = "JhGc10iJIU";
    /**
     * 令牌失效时间 2小时
     */

    private static final long TTLMILLIS = 7200000;

    /**
     * 签发者
     */
    private static final String ISS = "htwokey.com";

    /**
     * 生成token
     * @param user 用户信息
     * @return token
     */

    @Nullable
    public static String createToken(User user){

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

        //签发时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        // 失效时间
        long expMillis = nowMillis + TTLMILLIS;
        Date exp = new Date(expMillis);
        // header
        Map<String, Object> map = new HashMap<>(2+1);
        map.put("uid", user.getUid());
        map.put("username",user.getUid());


        //生成token
        JwtBuilder builder = Jwts.builder()
                .setClaims(map)
                .setIssuedAt(now)
                .setIssuer(ISS)
                .setExpiration(exp)
                .signWith(signatureAlgorithm,SECRET);
        return builder.compact();
    }

    /**
     * 解密 token
     * @param token token
     * @return map
     */
    private static Claims verifyToken(String token){

        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
        }catch (Exception e){
               throw new CommonException(500,false,"token已超时,请重新登录",null);
        }
    }

    /**
     * 获取token中的用户id
     * @param token token
     * @return 用户id
     */
    public static int getUid(String token){

        Claims claims = verifyToken(token);
        return (int) claims.get("uid");
    }

    /**
     * 判断token是否过期
     * @param date 时间
     * @param token token
     * @return true 过期，false 没过期
     */
    public static boolean checkExpiresAt(Date date,String token){

        //解密token
        Claims claims = verifyToken(token);

        // 计算token 过期时间
        Duration duration =  DateUtil.between(DateUtil.toLocalDateTime(claims.getExpiration()),DateUtil.toLocalDateTime(date));

        return duration.toMinutes() > 0;
    }


    public static void main(String[] args) {

        User user = new User();
        user.setUid(1024);
        user.setUsername("admin");

        String token = createToken(user);
        assert token != null;
        System.out.println(token.length());


        String token1 = "eyJhbGciOiJIUzUxMiJ9.eyJ1aWQiOjEwMjQsImlzcyI6Imh0d29rZXkueGluIiwiZXhwIjoxNTU2Mzg0NjY4LCJpYXQiOjE1NTYzNzc0NjgsImp0aSI6ImM1ZjRjYjExLTk5YmQtNGJmMC04MmZlLTA" +
                "zYzY2YjE3YTllMSIsInVzZXJuYW1lIjoxMDI0fQ.at4nJ5q1NP8Lwj72wryl565HmBmG5ERJepqP2ThzCvDfjZWb-IRvMegGenv4V1obA1QHOsN7H_e1TPtDCnFArQ";
        System.out.println(token1.length());

    }

}
