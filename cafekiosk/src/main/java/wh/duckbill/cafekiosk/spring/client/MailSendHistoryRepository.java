package wh.duckbill.cafekiosk.spring.client;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MailSendHistoryRepository extends JpaRepository<MailSendHistory, Long>{
        }
