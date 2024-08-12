package wh.duckbill.cafekiosk.spring.api.service.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import wh.duckbill.cafekiosk.spring.client.MailSendClient;
import wh.duckbill.cafekiosk.spring.client.MailSendHistory;
import wh.duckbill.cafekiosk.spring.client.MailSendHistoryRepository;
import wh.duckbill.cafekiosk.spring.domain.order.Order;
import wh.duckbill.cafekiosk.spring.domain.order.OrderRepository;
import wh.duckbill.cafekiosk.spring.domain.order.OrderStatus;
import wh.duckbill.cafekiosk.spring.domain.orderproduct.OrderProductRepository;
import wh.duckbill.cafekiosk.spring.domain.product.Product;
import wh.duckbill.cafekiosk.spring.domain.product.ProductRepository;
import wh.duckbill.cafekiosk.spring.domain.product.ProductSellingStatus;
import wh.duckbill.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static wh.duckbill.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static wh.duckbill.cafekiosk.spring.domain.product.ProductType.HANDMADE;


@ActiveProfiles("test")
@SpringBootTest
class OrderStatisticsServiceTest {
    @Autowired
    private OrderStatisticsService orderStatisticsService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;
    @MockBean
    private MailSendClient mailSendClient;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

    @DisplayName("결제완료 주문들을 조회하여 매출 통계 메일을 전송한다.")
    @Test
    void sendOrderStatisticsMail() {
        // given
        LocalDateTime now = LocalDateTime.of(2024, 8, 12, 21, 52);
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", HANDMADE, SELLING, "아메리카노", 4000);
        Product product3 = createProduct("003", HANDMADE, SELLING, "아메리카노", 4000);
        List<Product> products = List.of(product1, product2, product3);
        productRepository.saveAll(products);

        Order order1 = createPaymentCompleteOrder(products, LocalDateTime.of(2024, 8, 11, 23, 59, 59));
        Order order2 = createPaymentCompleteOrder(products, now);
        Order order3 = createPaymentCompleteOrder(products, LocalDateTime.of(2024, 8, 12, 23, 59, 59));
        Order order4 = createPaymentCompleteOrder(products, LocalDateTime.of(2024, 8, 13, 0, 0, 0));
        orderRepository.saveAll(List.of(order1, order2, order3, order4));

        // stubbing
        // Mock 객체에 원하는 행위를 정의
        Mockito.when(mailSendClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
                .thenReturn(true);
        // when
        boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2024, 8, 12), "test@test.com");

        // then
        assertThat(result).isTrue();

        List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
        assertThat(histories).hasSize(1)
                .extracting("content")
                .contains("총 매출 합계는 24000원 입니다.");

    }

    private static Order createPaymentCompleteOrder(List<Product> products, LocalDateTime now) {
        return Order.builder()
                .products(products)
                .orderStatus(OrderStatus.PAYMENT_COMPLETED)
                .registeredDateTime(now)
                .build();
    }

    private Product createProduct(String productNumber, ProductType productType, ProductSellingStatus sellingStatus, String name, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(productType)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }
}