package com.company.service;

import com.company.dto.ApiResponse;
import com.company.dto.TableOrderDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TableOrderService {
    ApiResponse<?> create(TableOrderDTO dto);

    ApiResponse<?> update(Integer id, TableOrderDTO dto);

    ApiResponse<?> delete(Integer id);

    ApiResponse<?> getById(Integer id);

    List<?> getList();

    Page<?> paging(int page, int size);

}
