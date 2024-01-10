package com.company.service;

import com.company.dto.ApiResponse;
import com.company.dto.CategoryDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    ApiResponse<?> create(CategoryDTO dto);

    ApiResponse<?> update(Integer id, CategoryDTO dto);

    ApiResponse<?> delete(Integer id);

    ApiResponse<?> getById(Integer id);

    List<?> getList();

    Page<?> paging(int page, int size);

}
