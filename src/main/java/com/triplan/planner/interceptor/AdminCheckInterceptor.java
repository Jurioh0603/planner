package com.triplan.planner.interceptor;

import com.triplan.planner.user.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class AdminCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        if(session != null && session.getAttribute("loginMemberInfo") != null) {
            UserDto userInfo = (UserDto) session.getAttribute("loginMemberInfo");
            if(!userInfo.getGrade().equals("9999")) {
                log.info("미인증 사용자 요청");
                //로그인으로 redirect
                response.sendRedirect("/error/forbidden");
                return false;
            }
        }

        return true;
    }
}
