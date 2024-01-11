package com.company.service;

import com.company.dto.ApiResponse;
import com.company.dto.CheckDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CheckService {
    ApiResponse<?> create(Boolean useCashback, CheckDTO dto);

    ApiResponse<?> update(String id, CheckDTO dto);

    ApiResponse<?> delete(String id);

    ApiResponse<?> getById(String id);

    List<?> getList();

    Page<?> paging(int page, int size);
}
