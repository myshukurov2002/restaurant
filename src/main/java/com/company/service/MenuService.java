package com.company.service;

import com.company.dto.ApiResponse;
import com.company.dto.MenuDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MenuService {
    ApiResponse<?> create(MenuDTO dto);

    ApiResponse<?> update(String id, MenuDTO dto);

    ApiResponse<?> delete(String id);

    ApiResponse<?> getById(String id);

    List<?> getList();

    Page<?> paging(int page, int size);

}
