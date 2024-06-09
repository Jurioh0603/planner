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
            //로그인한 계정이 admin 계정인지 체크
            if(!userInfo.getGrade().equals("9999")) {
                log.info("미인증 사용자 요청");
                //admin 계정이 아니라면 forbidden 에러 페이지로 redirect
                response.sendRedirect("/error/forbidden");
                return false;
            }
        }

        return true;
    }
}
