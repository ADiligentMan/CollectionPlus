package com.example.acer.collectionplus.Helper;

import android.util.Base64;
import android.util.Log;

import java.util.Date;
import java.util.HashMap;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTHelper {
    public static String generateJwt(String username) {
        Date IssueTime = new Date();
        Long var1 = Long.valueOf(IssueTime.getTime());
        Date ExpirationTime = new Date(var1.longValue() + 200000L);
        HashMap var3 = new HashMap();
        var3.put("typ", "JWT");
        var3.put("alg", "HS256");
        String jwtString = "";

        try {
            jwtString = Jwts.builder().
                    setHeader(var3).
                    setIssuer(username).    //请求的发起者；
                    setIssuedAt(IssueTime).          //请求的发起时间；
                    setExpiration(ExpirationTime).        //expTime是过期时间，当前时间+200秒；
                    signWith(SignatureAlgorithm.HS256, Base64.encode(username.getBytes("UTF-8"),0)).   //两个参数，一个是加密算法，一个秘钥；SECRET_KEY是加密算法对应的密钥，这里使用额是HS256加密算法；
                    claim("key","vaule").              //该方法是在JWT中加入值为vaule 的 key 自定义字段；
                    compact();
        } catch (Exception var6) {
            Log.e("HttpRequest", "octo generate error...", var6);
        }

        return jwtString;
    }
}
