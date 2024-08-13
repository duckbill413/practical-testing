package wh.duckbill.cafekiosk.spring.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MailSendClient {
    public boolean sendEmail(String fromEmail, String toEmail, String subject, String content) {
        log.info("메일 전송");

        throw new IllegalArgumentException("메일 전송");
    }

    // 기타 기능을 가진 메서드
    public void a() {
        log.info("MailSendClient A");
    }

    public void b() {
        log.info("MailSendClient B");
    }

    public void c() {
        log.info("MailSendClient C");
    }
}
