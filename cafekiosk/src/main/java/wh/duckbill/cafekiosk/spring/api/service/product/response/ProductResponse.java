package wh.duckbill.cafekiosk.spring.api.service.product.response;

import lombok.Builder;
import lombok.Getter;
import wh.duckbill.cafekiosk.spring.domain.product.Product;
import wh.duckbill.cafekiosk.spring.domain.product.ProductSellingStatus;
import wh.duckbill.cafekiosk.spring.domain.product.ProductType;

@Getter
public class ProductResponse {
    private final Long id;
    private final String productNumber;
    private final ProductType type;
    private final ProductSellingStatus sellingStatus;
    private final String name;
    private final int price;

    @Builder
    private ProductResponse(Long id, String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.id = id;
        this.productNumber = productNumber;
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .sellingStatus(product.getSellingStatus())
                .type(product.getType())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
