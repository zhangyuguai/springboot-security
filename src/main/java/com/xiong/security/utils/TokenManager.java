package com.xiong.security.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.jsonwebtoken.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@ConfigurationProperties(prefix = "jwt")
@Slf4j
@Data
public class TokenManager {
    @Autowired
    private RedisTemplate redisTemplate;

    //token有效时长
    private long tokenEcpiration ;
    //编码秘钥
    private String tokenSignKey ;
    //1 使用jwt根据用户名生成token
    public String createToken(String username) {
        log.info("秘钥{}",tokenSignKey);
        log.info("过期时间{}",tokenEcpiration);
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+tokenEcpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }
    //2 根据token字符串得到用户信息
    public String getUserInfoFromToken(String token) {
        String userinfo;
        try {
            userinfo= Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
            return userinfo;
        } catch (ExpiredJwtException e) {
            //token已过期，从异常中获取
            Claims claims = e.getClaims();
            userinfo = claims.getSubject();
        }
        return userinfo;
    }

    /**
     * 从token中获取数据声明claim
     *
     * @param token 令牌token
     * @return 数据声明claim
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims  = Jwts.parser()
                    .setSigningKey(tokenSignKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        }
        return claims;
    }


    //获取token过期时间
    public Date getExpirationFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    /**
     * 判断token是否过期
     *
     * @param token 令牌
     * @return 是否过期：已过期返回true，未过期返回false
     */
    public Boolean isTokenExpired(String token) {
        Date expiration = getExpirationFromToken(token);
        return expiration.before(new Date());
    }

    //3 删除token
    public void removeToken(String userName) {
        redisTemplate.delete(userName);
    }
    /**
     * 刷新令牌
     *
     * @param token 原令牌
     * @return 新令牌
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            String username = claims.getSubject();
            refreshedToken = createToken(username);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }
}