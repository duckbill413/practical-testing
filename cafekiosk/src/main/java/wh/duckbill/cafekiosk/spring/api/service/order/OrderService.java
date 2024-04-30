package wh.duckbill.cafekiosk.spring.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wh.duckbill.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import wh.duckbill.cafekiosk.spring.api.service.order.response.OrderResponse;
import wh.duckbill.cafekiosk.spring.domain.order.Order;
import wh.duckbill.cafekiosk.spring.domain.order.OrderRepository;
import wh.duckbill.cafekiosk.spring.domain.product.Product;
import wh.duckbill.cafekiosk.spring.domain.product.ProductRepository;
import wh.duckbill.cafekiosk.spring.domain.product.ProductType;
import wh.duckbill.cafekiosk.spring.domain.stock.Stock;
import wh.duckbill.cafekiosk.spring.domain.stock.StockRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    /**
     * 재고 감소 -> 동시성 고민 (동시에 차감 요청이 왔을때)
     * optimistic lock / pessimistic lock
     */
    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();
        List<Product> products = findProductBy(productNumbers);

        deductStockQuantities(products);

        Order order = Order.create(products, registeredDateTime);
        orderRepository.save(order);

        return OrderResponse.of(order);
    }

    private void deductStockQuantities(List<Product> products) {
        var stockProductNumbers = extractStockProductNumbers(products);

        Map<String, Stock> stockMap = createStockMapBy(stockProductNumbers);

        Map<String, Long> productCountingMap = createCountingMapBy(stockProductNumbers);

        for (String stockProductNumber : new HashSet<>(stockProductNumbers)) {
            Stock stock = stockMap.get(stockProductNumber);
            int quantity = productCountingMap.get(stockProductNumber).intValue();

            if (stock.isQuantityLessThan(quantity)) {
                throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
            }

            stock.deductQuantity(quantity);
        }
    }

    private static List<String> extractStockProductNumbers(List<Product> products) {
        return products.stream()
                .filter(product -> ProductType.containsStockType(product.getType()))
                .map(Product::getProductNumber)
                .toList();
    }

    private Map<String, Stock> createStockMapBy(List<String> stockProductNumbers) {
        return stockRepository.findByProductNumberIn(stockProductNumbers)
                .stream().collect(Collectors.toMap(Stock::getProductNumber, s -> s));
    }

    private static Map<String, Long> createCountingMapBy(List<String> stockProductNumbers) {
        return stockProductNumbers.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
    }

    private List<Product> findProductBy(List<String> productNumbers) {
        List<Product> products = productRepository.findByProductNumberIn(productNumbers);
        Map<String, Product> productMap = products.stream().collect(Collectors.toMap(Product::getProductNumber, p -> p));

        return productNumbers.stream().map(productMap::get).toList();
    }
}
