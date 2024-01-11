package com.company.exp.handler;


import com.company.dto.ApiResponse;
import com.company.exp.AppBadRequestException;
import com.company.exp.ItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.MethodNotAllowedException;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({ItemNotFoundException.class})
    public ResponseEntity<ApiResponse<?>> handlerException(ItemNotFoundException e) {
        return ResponseEntity.ok(new ApiResponse<>(e.getMessage(), 404, true));
    }

    @ExceptionHandler({AppBadRequestException.class})
    public ResponseEntity<ApiResponse<?>> handlerException(AppBadRequestException e) {
        return ResponseEntity.ok(new ApiResponse<>(e.getMessage(), 400, true));
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<ApiResponse<?>> handler(MethodNotAllowedException exp) {
        return ResponseEntity.ok(new ApiResponse<>(exp.getMessage(), 405, true));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handler(AccessDeniedException e) {
        return ResponseEntity.ok(new ApiResponse<>(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED.value(), true));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<?> handlerException(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.ok(new ApiResponse<>(e.getMessage(), 500, true));
    }
}
