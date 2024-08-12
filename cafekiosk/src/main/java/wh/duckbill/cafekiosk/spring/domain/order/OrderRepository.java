package wh.duckbill.cafekiosk.spring.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = """
            SELECT o
            FROM Order o
            WHERE o.registeredDateTime >= :startDateTime
                AND o.registeredDateTime < :endDateTime
                AND o.orderStatus = :orderStatus
            """)
    List<Order> findOrderBy(LocalDateTime startDateTime, LocalDateTime endDateTime, OrderStatus orderStatus);
}
