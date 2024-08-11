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
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();
        // Product
        List<Product> products = findProductBy(productNumbers);

        Order order = Order.create(products, registeredDateTime);
        orderRepository.save(order);

        return OrderResponse.of(order);
    }

    private List<Product> findProductBy(List<String> productNumbers) {
        List<Product> products = productRepository.findByProductNumberIn(productNumbers);
        Map<String, Product> productMap = products.stream().collect(Collectors.toMap(Product::getProductNumber, p -> p));

        return productNumbers.stream().map(productMap::get).toList();
    }
}
