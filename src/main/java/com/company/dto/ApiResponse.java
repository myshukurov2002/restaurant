package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private String message; // Not found
    private Integer code; // 400
    private Boolean status; // false
    private T data; // {}

    public ApiResponse(String message, Integer code, Boolean status) {
        this.message = message;
        this.code = code;
        this.status = status;
    }

    public ApiResponse(Boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiResponse(Boolean status, T data) {
        this.status = status;
        this.data = data;
    }

    public ApiResponse(Boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
