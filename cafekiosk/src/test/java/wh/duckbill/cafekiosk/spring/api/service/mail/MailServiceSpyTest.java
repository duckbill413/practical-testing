package wh.duckbill.cafekiosk.spring.api.service.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import wh.duckbill.cafekiosk.spring.client.MailSendClient;
import wh.duckbill.cafekiosk.spring.client.MailSendHistory;
import wh.duckbill.cafekiosk.spring.client.MailSendHistoryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class) // Mock 객체를 사용을 알림
class MailServiceSpyTest {

    @Spy // Spy 객체 생성
    private MailSendClient mailSendClient;
    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks // Mock 객체를 Inject 해줌 DI와 동일한 역할 수행
    private MailService mailService;


    /**
     * MailSendClient 의 sendEmail 만 테스트하고
     * 나머지 a, b, c 메서드는 테스트하고 싶지 않고 싶을때
     */
    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail() {
        // given
        // Spy 는 when 절을 사용하는 대신 do 를 사용
        // Spy 는 실제 객체를 기반으로 생성되기 때문에 Stubbing이 되지 않음
        Mockito.doReturn(true)
                .when(mailSendClient)
                .sendEmail(anyString(), anyString(), anyString(), anyString());

        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        assertThat(result).isTrue();
        Mockito.verify(mailSendHistoryRepository, Mockito.times(1)).save(any(MailSendHistory.class));

    }

}