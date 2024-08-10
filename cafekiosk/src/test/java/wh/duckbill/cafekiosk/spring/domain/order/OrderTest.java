package wh.duckbill.cafekiosk.spring.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wh.duckbill.cafekiosk.spring.domain.product.Product;
import wh.duckbill.cafekiosk.spring.domain.product.ProductSellingStatus;
import wh.duckbill.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {
    @DisplayName("주문 생성시 상품 리스트에서 주문의 총 금액을 계산한다.")
    @Test
    void calculateTotalPrice() {
        // given
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 3000),
                createProduct("003", 5000)
        );

        // when
        Order order = Order.create(products, LocalDateTime.now());
        // then
        assertThat(order.getTotalPrice()).isEqualTo(9000);
    }

    @DisplayName("주문 생성시 주문 상태는 INIT 이다.")
    @Test
    void init() {
        // given
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 3000),
                createProduct("003", 5000)
        );

        // when
        Order order = Order.create(products, LocalDateTime.now());
        // then
        assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);
    }

    @DisplayName("주문 생성시 주문 등록 시간을 기록한다.")
    @Test
    void registeredDateTime() {
        // given
        LocalDateTime registeredDateTime = LocalDateTime.now();
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 3000),
                createProduct("003", 5000)
        );

        // when
        Order order = Order.create(products, registeredDateTime);
        // then
        assertThat(order.getRegisteredDateTime()).isEqualTo(registeredDateTime);
    }

    private Product createProduct(String productNumber, int price) {
        return Product.builder()
                .type(ProductType.HANDMADE)
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("메뉴 이름")
                .build();
    }
}