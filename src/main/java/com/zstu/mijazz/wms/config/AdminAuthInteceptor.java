package com.zstu.mijazz.wms.config;

import com.auth0.jwt.interfaces.Claim;
import com.zstu.mijazz.wms.ResultReturn;
import com.zstu.mijazz.wms.Utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class AdminAuthInteceptor implements HandlerInterceptor {
    @Autowired
    TokenUtil tokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String token = request.getHeader("Access-Token");
        if (token == null) {
            token = request.getParameter("Access-Token");
        }

        if (token == null) {
            noAuthGo(response);
            return false;
        }

        Map<String, Claim> claims = tokenUtil.verify(token);


        if (claims != null && claims.get("isAdmin").asBoolean()) {
            request.setAttribute("emId", claims.get("emId"));
            request.setAttribute("emName", claims.get("emName"));
            return true;
        } else {
            badAuthGo(response);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public static void noAuthGo(HttpServletResponse response) throws IOException {
        response.sendRedirect("/login?noauth");
    }

    public static void badAuthGo(HttpServletResponse response) throws IOException {
        response.getWriter().println(new ResultReturn<>(302, "Bad Auth", "Low Auth for Admin API"));
    }
}
