package com.neo.lesson.manage;

import com.elon.base.util.JWTUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截rest接口请求，做用户合法性认证.
 *
 * @author neo
 * @since 2025-02-28
 */
public class JWTInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LogManager.getLogger(JWTInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 提起请求头中的jwt信息
        String jwtToken = request.getHeader("jwt-token");

        try {
            JWTUtil.instance().verifyJWT(jwtToken);
            response.setHeader("Access-Control-Allow-Origin", "*");
            return true;
        } catch (Exception e){
            LOGGER.error("Verify token fail.", e);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println("Token is valid");
            return false;
        }
    }
}
