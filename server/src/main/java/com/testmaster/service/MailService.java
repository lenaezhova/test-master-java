package com.testmaster.service;

import com.testmaster.config.jwt.JwtAuthenticationFilter;
import com.testmaster.exeption.ClientException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;

    private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public void sendConfirmEmail(String to, String link) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String subject = "Активация аккаунта на " + System.getenv("CLIENT_URL");
            String htmlContent = "<div>" +
                    "<h1>Для активации перейдите по ссылке</h1>" +
                    "<a href=\"" + link + "\">" + "Активировать" + "</a>" +
                    "</div>";

            helper.setFrom(System.getenv("SMTP_USER"));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            LOG.error("{}", e.getMessage());
            throw new ClientException(e.getMessage(), HttpStatus.CONFLICT.value());
        }
    }

}
