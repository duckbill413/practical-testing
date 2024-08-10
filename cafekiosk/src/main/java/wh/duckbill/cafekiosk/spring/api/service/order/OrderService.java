package wh.duckbill.cafekiosk.spring.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wh.duckbill.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import wh.duckbill.cafekiosk.spring.api.service.order.response.OrderResponse;
import wh.duckbill.cafekiosk.spring.domain.order.Order;
import wh.duckbill.cafekiosk.spring.domain.order.OrderRepository;
import wh.duckbill.cafekiosk.spring.domain.product.Product;
import wh.duckbill.cafekiosk.spring.domain.product.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();
        // Product
        List<Product> products = productRepository.findByProductNumberIn(productNumbers);

        Order order = Order.create(products, registeredDateTime);
        orderRepository.save(order);

        return OrderResponse.of(order);
    }
}
