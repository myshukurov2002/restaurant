package com.company.service;

import com.company.dto.ApiResponse;
import com.company.dto.OrderDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    ApiResponse<?> create(OrderDTO dto);

    ApiResponse<?> update(String id, OrderDTO dto);

    ApiResponse<?> delete(String id);

    ApiResponse<?> getById(String id);

    List<?> getList();

    Page<?> paging(int page, int size);
}
