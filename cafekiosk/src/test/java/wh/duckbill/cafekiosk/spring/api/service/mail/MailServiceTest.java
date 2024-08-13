package wh.duckbill.cafekiosk.spring.api.service.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import wh.duckbill.cafekiosk.spring.client.MailSendClient;
import wh.duckbill.cafekiosk.spring.client.MailSendHistory;
import wh.duckbill.cafekiosk.spring.client.MailSendHistoryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class MailServiceTest {

    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail() {
        // given
        MailSendClient mailSendClient = Mockito.mock(MailSendClient.class);
        MailSendHistoryRepository mailSendHistoryRepository = Mockito.mock(MailSendHistoryRepository.class);

        MailService mailService = new MailService(mailSendClient, mailSendHistoryRepository);
        Mockito.when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(true);
//        Mockito.when(mailSendHistoryRepository.save(any(MailSendHistory.class)))
//                .thenReturn();

        // when
        boolean result = mailSendClient.sendEmail("", "", "", "");

        // then
        assertThat(result).isTrue();

    }

}