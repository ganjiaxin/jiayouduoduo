package com.hyk.code.common.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

/**
 * @Auther: 霍中曦
 * @Date: 2018/12/4 16:46
 * @Description:
 */
public class TestToken {

    public static String getToken(String json_py){
        String signKey =PropertiesUtil.getValue("signingKey");//此处就是在服务器定义的自己的密匙
        try {
            // The JWT signature algorithm we will be using to sign the token
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            // We will sign our JWT with our ApiKey secret
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(signKey);
            Key signingKey = new SecretKeySpec(apiKeySecretBytes,signatureAlgorithm.getJcaName());

            // Let's set the JWT Claims
            JwtBuilder builder = Jwts.builder()
                    .setPayload(json_py.toString())
                    .signWith(signatureAlgorithm, signingKey);
            return builder.compact();
        } catch(Exception e) {
            return "error";
        }
    }

    /**
     * 判断是否token值看是否登录成功
     * @return
     */
    public static String isLogin(String jwt){
        String signingKey =PropertiesUtil.getValue("signingKey");//此处就是在服务器定义的自己的密匙
        String params="";
        if (jwt.split("\\.").length == 3) {
            String header = jwt.split("\\.")[0];
            String payload = jwt.split("\\.")[1];
            //System.out.println(Base64Codec.BASE64URL.decodeToString(header));
            //System.out.println(Base64Codec.BASE64URL.decodeToString(payload));
            String sign = jwt.split("\\.")[2];//带过来的签名
            String headerNew = getToken(Base64Codec.BASE64URL.decodeToString(payload)).split("\\.")[0];
            String signNew = getToken(Base64Codec.BASE64URL.decodeToString(payload)).split("\\.")[2];
            /*
            System.out.println("新的token："+getToken(Base64Codec.BASE64URL.decodeToString(payload)));
            System.out.println(signNew);*/

            if(header.equals(headerNew) && sign.equals(signNew)){//进行安全校验（头部和签名）
                Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(signingKey)).parseClaimsJws(jwt).getBody();
                if(claims!=null){
                    long expdate = (Long) claims.get("expDate");
                    long nowMillis = System.currentTimeMillis();
                    if(expdate>nowMillis){//判断token有效性
                        params="success";//校验成功，有此用户
                    }else{
                        params="timeout";
                    }
                }
            }else{
                params="修改数据--faile";
            }

        }
        return params;
    }



    public static void main(String[] args) {

        String tokenDel="eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwicGFzc3dvcmQiOiI5ODRmNDVlNzIxMjFiYjE0NjMzMmRmMWNkYjZjYTllZWY5MWFhOGJmY2JjMDdiNzAxYzM0ZjEzMCIsImlkIjoiZTA0MThhYmI5YmE3NDE1MGE3Y2JkOThiY2Q3ZjM0NWMiLCJleHBEYXRlIjoxNTQzOTE0NTU4MjQ4fQ.SUg7inQz6bNZLRfDwmSV3eqDK0IDwOyuX0khyzR2F8U";

        System.out.println(TestToken.isLogin(tokenDel));
    }

}
