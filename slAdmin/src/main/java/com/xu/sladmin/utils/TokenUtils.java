package com.xu.sladmin.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.lang.time.DateUtils;

/**
 * token工具类
 *token过期时间
 * @Name TokenUtils
 * @Author 31671
 * @Date 2023-10-12 20:29
 */
public class TokenUtils {

   /**
   * @Date : 2023/10/12 20:41
   * @Author: xu
    * @Param :用户名和用户密码
   */
    public static String getToken(String uerId,String sign){

     return    JWT.create().withAudience(uerId) //将uerId传入作为载荷
                .withExpiresAt(DateUtils.addHours(new java.util.Date(), 2))  //过期时间为2小时，当前时间加两小时
                .sign(Algorithm.HMAC256(sign));   //以用户密码为密钥

    }
}
