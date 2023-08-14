package com.fintech.web.interceptor;

import com.fintech.exception.CustomUnAuthorizedException;
import com.fintech.web.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession();

        log.info("interceptor {}", session.getAttribute(SessionConst.LOGIN_USER));
        log.info("handler {}", handler.getClass());

        if (session == null ||
                session.getAttribute(SessionConst.LOGIN_USER) == null){
            throw new CustomUnAuthorizedException("로그인이 필요한 서비스입니다.");
        }
        return true;
    }
}
