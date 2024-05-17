package wh.duckbill.cafekiosk.spring.api;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {
    private int code;
    private HttpStatus status;
    private String message;
    private T data;

    @Builder
    private ApiResponse(HttpStatus status, String message, T data) {
        this.code = status.value();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> of(HttpStatus status, T data) {
        return ApiResponse.<T>builder()
                .status(status)
                .message(status.name())
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> of(HttpStatus status, String message, T data) {
        return ApiResponse.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.<T>builder()
                .status(HttpStatus.OK)
                .message(HttpStatus.OK.name())
                .data(data)
                .build();
    }
}
