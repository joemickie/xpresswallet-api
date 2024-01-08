package com.chukwuemeka.xpresswalletapi.payloads.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T>{
    private String message;
    private HttpStatus status;
    private T data;
    private Integer statusCode;

    public ApiResponse(String message, HttpStatus status) {
        this.data = null;
        this.message = message;
        this.status = status;
        this.statusCode = status.value();
    }

    public ApiResponse(T data) {
        this.data = data;
        this.status = HttpStatus.OK;
        this.statusCode = HttpStatus.OK.value();
        this.message = "Success";
    }
}
