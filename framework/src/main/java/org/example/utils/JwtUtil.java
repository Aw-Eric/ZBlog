package org.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    // 有效期为
    public static final long JWT_TTL = 60 * 60 * 1000L; // 1小时
    // 设置密钥明文
    public static final String JWT_KEY = "spse";

    public static String getUUID() {
        String token = UUID.randomUUID().toString().replace("-", "");
        return token;
    }

    /**
     * 创建JWT
     * @param subject subject token中要存放的数据（json格式）
     * @return JWT
     */
    public static String createJWT(String subject) {
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID()); // 设置过期时间
        return builder.compact();
    }

    /**
     * 创建JWT
     * @param subject subject token中要存放的数据（json格式）
     * @param ttlMillis 有效期
     * @return JWT
     */
    public static String createJWT(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID()); // 设置过期时间
        return builder.compact();
    }
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)    // 唯一的ID
                .setSubject(subject)    // 主题 可以是JSON数据
                .setIssuer("spse")  // 签发者
                .setIssuedAt(now)   // 签发时间
                .signWith(signatureAlgorithm, secretKey)    // 使用HS256对称加密算法，第二个参数是密钥
                .setExpiration(expDate);    // 设置过期时间
    }
    /**
     * 创建JWT
     * @param id 唯一ID
     * @param subject subject token中要存放的数据（json格式）
     * @param ttlMillis 有效期
     * @return JWT
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id); // 设置过期时间
        return builder.compact();
    }

    public static void main(String[] args) {
        String token = createJWT("1234");
        System.out.println(token);
        Claims claims = parseJWT(token);
        System.out.println(claims);
    }

    /**
     * 生成加密后的密钥 secretKey
     * @return SecretKey
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.decodeBase64(JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 解析JWT
     * @param jwt JWT
     * @return Claims
     */
    public static Claims parseJWT(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
