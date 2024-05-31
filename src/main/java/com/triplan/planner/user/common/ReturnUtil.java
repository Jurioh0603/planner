package com.triplan.planner.user.common;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReturnUtil {

    //리턴 메세지&URL 처리
    public static void setReturnMessage(HttpServletResponse response, String message, String message2, String status, String returnUrl) {

        log.info("===============ReturnUtil 메세지 처리 공통 메소드 START ==================");

        response.setContentType("text/html; charset=euc-kr");
        PrintWriter out;

        try {

            //스위트알럿 라이브러리를 이용하여 경고창을 꾸밈
            out = response.getWriter();
            out.println("<script src='https://code.jquery.com/jquery-1.12.4.min.js'></script>");
            out.println("<script src='https://cdn.jsdelivr.net/npm/sweetalert2@9'></script>");
            out.println("<script src='https://unpkg.com/sweetalert/dist/sweetalert.min.js'></script>");
            out.println("<script>");
            out.println("	$(document).ready(function(){");
            if(!"".equals(message2)) {
                out.println("		swal('" + message + "','" + message2 + "','" + status + "')");
            }else {
                out.println("		swal('" + message + "','','" + status + "')");
            }
            out.println("		.then(function() {");
            out.println("			location.href = '" + returnUrl + "';");
            out.println("		});");
            out.println("	})");
            out.println("</script>");
            out.flush();

            log.info("===============ReturnUtil 메세지 처리 공통 메소드 END ==================");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

