package com.triplan.planner.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String queryString = request.getQueryString();
        String requestURL = null;
        if(queryString == null)
            requestURL = request.getRequestURI();
        else
            requestURL = request.getRequestURI() + "?" + queryString;

        log.info("인증 체크 인터셉터 실행 {}", requestURL);
        HttpSession session = request.getSession();

        if(session == null || session.getAttribute("loginMemberInfo") == null) {
            log.info("미인증 사용자 요청");
            //redirectURI를 세션에 저장
            session.setAttribute("interceptorRedirectURL", requestURL);
            //로그인으로 redirect
            response.sendRedirect("/user/login");
            return false;
        }

        return true;
    }
}
