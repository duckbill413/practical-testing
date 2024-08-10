package wh.duckbill.cafekiosk.spring.domain.orderproduct;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wh.duckbill.cafekiosk.spring.domain.BaseEntity;
import wh.duckbill.cafekiosk.spring.domain.order.Order;
import wh.duckbill.cafekiosk.spring.domain.product.Product;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderProduct extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Builder
    public OrderProduct(Order order, Product product) {
        this.order = order;
        this.product = product;
    }
}
