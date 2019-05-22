package com.miok.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.miok.board.service.BoardSvc;


public class LoginInterceptor implements HandlerInterceptor {
    static final Logger LOGGER = LoggerFactory.getLogger(BoardSvc.class);

    // 일반 사용자의 로그인 체크 (Controller 실행 요청 전) 
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {
        HttpSession session = req.getSession();
        
        try {
            if (session == null || session.getAttribute("userno") == null) {
                res.sendRedirect("memberLogin"); 
                return false;
            }
        } catch (IOException ex) {
            LOGGER.error("LoginInterceptor");
        }
        
        return true;
    }
    
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView mv) {
    }
    
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex) {
    }

}
