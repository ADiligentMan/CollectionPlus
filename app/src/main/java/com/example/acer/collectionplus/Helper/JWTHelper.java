package com.example.acer.collectionplus.Helper;

import android.util.Base64;
import android.util.Log;

import java.util.Date;
import java.util.HashMap;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTHelper {
    public static String generateJwt() {
        Date var0 = new Date();
        Long var1 = Long.valueOf(var0.getTime());
        Date var2 = new Date(var1.longValue() + 200000L);
        HashMap var3 = new HashMap();
        var3.put("typ", "JWT");
        var3.put("alg", "HS256");
        String var4 = "";

        try {
            var4 = Jwts.builder().
                    setHeader(var3).
                   // setIssuer(sAuthOctoKey).    //请求的发起者；
                    setIssuedAt(var0).          //请求的发起时间；
                    setExpiration(var2).        //expTime是过期时间，当前时间+200秒；
                    //signWith(SignatureAlgorithm.HS256, Base64.encode(sAuthSecret.getBytes("UTF-8"))).   //两个参数，一个是加密算法，一个秘钥；SECRET_KEY是加密算法对应的密钥，这里使用额是HS256加密算法；
                    claim("key","vaule").              //该方法是在JWT中加入值为vaule 的 key 自定义字段；
                    compact();
        } catch (Exception var6) {
            Log.e("HttpRequest", "octo generate error...", var6);
        }

        return var4;
    }

}
