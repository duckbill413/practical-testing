package wh.duckbill.cafekiosk.spring.domain.stock;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findByProductNumberIn(List<String> productNumbers);
}
