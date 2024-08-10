package wh.duckbill.cafekiosk.spring.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * select *
     * from product
     * where selling_type in('SELLING', 'HOLD');
     */
    List<Product> findBySellingStatusIn(List<ProductSellingStatus> sellingTypes);

    List<Product> findByProductNumberIn(List<String> productNumbers);
}
