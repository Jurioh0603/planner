package com.triplan.planner.user.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendMail {

    private final JavaMailSender javaMailSender;

    //기본적으로 구글 SMTP 서버를 이용한다.
    //본인의 계정으로 다른 이용자에게 메일을 보내는 개념이다.
    //본인의 구글계정 아이디/앱비밀번호 정보를 application.properties에 정의한다.
    /**
     * @methodName    : sendEmailToMember
     * @description     : 메일전송
     * @author        :
     * @date        : 2024.05.30
     * @param toEmailAddr    받는사람이메일주소
     * @param subject      제목
     * @param text         내용
     */
    public void sendEmailToMember(String toEmailAddr, String subject, String text) {
        log.info("===============SendMail 메일전송 메소드 START ==================");
        log.info("======== 받는 사람 메일 : {} ==== 내용 {}",toEmailAddr,text);
        SimpleMailMessage message = new SimpleMailMessage();
        try {
            //받는사람
            message.setTo(toEmailAddr);
            //제목
            message.setSubject(subject);
            //내용
            message.setText(text);
            //이거를 안넣어서 뻘짓하였음. send메소드 그
            javaMailSender.send(message);

        } catch (MailException e) {
            // 메일 전송이 실패하면 예외가 발생
            e.printStackTrace();
            log.info("메일전송에러 : {}",e.getMessage());
        }
        log.info("===============SendMail 메일전송 메소드 END ==================");
    }
}

