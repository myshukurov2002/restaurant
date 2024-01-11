package com.company.service.impl;

import com.company.config.i18n.ResourceBundleService;
import com.company.config.security.details.SecurityUtil;
import com.company.dto.ApiResponse;
import com.company.dto.CategoryDTO;
import com.company.entity.CategoryEntity;
import com.company.repository.CategoryRepository;
import com.company.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;
    private CategoryEntity toEntity(CategoryDTO dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());
        entity.setFoodEntities(dto.getFoodEntities());
        return entity;
    }
    private CategoryDTO toDTO(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setName(entity.getName());
        dto.setFoodEntities(entity.getFoodEntities());
        return dto;
    }

    @Override
    public ApiResponse<?> create(CategoryDTO dto) {
        CategoryEntity entity = toEntity(dto);
        entity.setOwnerId(SecurityUtil.getCurrentProfileId());

        log.info("category created " + entity.getId());

        CategoryEntity saved = categoryRepository.save(entity);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.created", SecurityUtil.getProfileLanguage()), toDTO(saved));
    }
    @Override
    public ApiResponse<?> update(Integer id, CategoryDTO dto) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        CategoryEntity entity = optionalCategory.get();
        entity.setName(dto.getName());

        log.warn("category updated " + id);

        CategoryEntity saved = categoryRepository.save(entity);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.updated", SecurityUtil.getProfileLanguage()), toDTO(saved));
    }
    @Override
    public ApiResponse<?> delete(Integer id) {
        if (!categoryRepository.existsById(id)) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        categoryRepository.deleteById(id);
        log.warn("category deleted " + id);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.deleted", SecurityUtil.getProfileLanguage()));
    }
    @Override
    public ApiResponse<?> getById(Integer id) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        CategoryEntity entity = optionalCategory.get();
        return new ApiResponse<>(true, toDTO(entity));
    }

    @Override
    public List<CategoryDTO> getList() {
        List<CategoryEntity> all = categoryRepository.findAll();
        return all
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public Page<CategoryDTO> paging(int page, int size) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CategoryEntity> entities = categoryRepository.findAll(pageable);
        List<CategoryDTO> dtos = entities
                .stream()
                .map(this::toDTO)
                .toList();
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }
}
