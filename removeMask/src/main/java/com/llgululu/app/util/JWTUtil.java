package com.llgululu.app.util;



import com.llgululu.app.entity.Userinfo;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {
    //JWT秘钥
    private static final String AUTHORIZE_TOKEN_SECRET = "llgululu";
    //JWT过期时间，单位毫秒。 1*2*60*60*1000=7200000 2小时
    private static final long AUTHORIZE_TOKEN_EXPIRE = 7200000;

    public static String createJwt(Userinfo user) {
        //jwt的加密算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //获取当前时间戳,生成过期时间
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + AUTHORIZE_TOKEN_EXPIRE;
        Date expDate = new Date(expMillis);
        //token的签发时间
        Date now = new Date(nowMillis);
        //需要保存到token字符串的有用信息
        Map<String, Object> map = new HashMap<>();
        map.put("uId", user.getuId());
        map.put("uOpenid", user.getuOpenId());
        JwtBuilder builder = Jwts.builder()
                .setClaims(map)  //设置附加信息
                .setIssuer("llgululu")     // 签发者
                .setIssuedAt(now)      // 签发时间
                .signWith(signatureAlgorithm, AUTHORIZE_TOKEN_SECRET)  // 签名算法以及密匙
                .setExpiration(expDate); // 过期时间
        return builder.compact();
    }

    /**
     * 验证JWT
     */

    public static R<Claims> validateJWT(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(AUTHORIZE_TOKEN_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            return R.ok(claims);
        } catch (ExpiredJwtException e) {
            return R.error(2,"token过期");
        } catch (SignatureException e) {
            return R.error(3,"token校验异常");
        } catch (Exception e) {
            return R.error(4,"token异常");
        }
    }
}