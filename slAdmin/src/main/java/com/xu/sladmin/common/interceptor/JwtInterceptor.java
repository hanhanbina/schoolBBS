package com.xu.sladmin.common.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xu.sladmin.common.Constants;
import com.xu.sladmin.entity.User;
import com.xu.sladmin.exception.ServiceException;
import com.xu.sladmin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token验证拦截器
 *
 * @Name JwtInterceptor
 * @Author 31671
 * @Date 2023-10-21 23:01
 */
public class JwtInterceptor implements HandlerInterceptor {



    @Autowired
    private  IUserService iUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
      String token = request.getHeader("token");
      //如果不是映射到方法直接通过
      if (!(handler instanceof HandlerMethod)){
          return true;
      }
    //执行认证
        if(StringUtils.isBlank(token)){
            throw  new ServiceException(Constants.CODE_400, "无token");
        }
        //获取token中的user_id
        String userId;
    try {
        userId = JWT.decode(token).getAudience().get(0);
    } catch (JWTDecodeException j) {
        throw new ServiceException(Constants.CODE_400, "token验证失败");
    }
//根据token中的id查询数据库
//         Admin admin = iAdminService.getById(userId);
        User user = iUserService.getById(userId);
        if (user == null){
            throw new ServiceException(Constants.CODE_400, "用户不存在");
        }
        //验证token，根据用户密码加签验证
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUserPassword())).build();
        try {
            //使用ver方法验证
            jwtVerifier.verify(token);
        }catch (JWTVerificationException e){
            throw new ServiceException(Constants.CODE_400, "token验证失败");
        }

        return true;
    }
}
