package com.company.service;

import com.company.dto.ApiResponse;
import com.company.dto.FoodDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FoodService {
    ApiResponse<?> create(FoodDTO dto);

    ApiResponse<?> update(Integer id, FoodDTO dto);

    ApiResponse<?> delete(Integer id);

    ApiResponse<?> getById(Integer id);

    List<?> getList();

    Page<?> paging(int page, int size);

    Page<?> pagingByCategoryId(Integer categoryId, int page, int size);
}
