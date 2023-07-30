package com.fintech.service.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class MailAuthentication implements AuthenticationMethod {
    @Autowired
    JavaMailSender javaMailSender;

    private MimeMessage createMessage(String to, String authCode) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {

            message.addRecipients(Message.RecipientType.TO, to);
            message.setSubject("[인증번호] 회원가입 인증 메일");

            // todo 공통화가 필요

            String msg = "";
            msg += "<div style='margin:20px;'>";
            msg += "<h1> 안녕하세요. Fintech 서비스 입니다. </h1>";
            msg += "<br>";
            msg += "<p>아래 코드를 복사해 입력해주세요<p>";
            msg += "<p>감사합니다.<p>";
            msg += "<br>";
            msg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
            msg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
            msg += "<div style='font-size:140%'>";
            msg += "Authentication code : <strong>";
            msg += authCode + "</strong><div><br/> ";
            msg += "</div>";
            message.setText(msg, "utf-8", "html");
            message.setFrom(new InternetAddress("yskwon0619@gmail.com", "DevFrog"));
        } catch (Exception e) {
            // todo custom exception
        }

        return message;
    }

    @Override
    public void sendMessage(String to, String authCode) {
        MimeMessage message = createMessage(to, authCode);

        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            log.info("mail exception", e);
            throw new IllegalArgumentException(e);
        }
    }
}
