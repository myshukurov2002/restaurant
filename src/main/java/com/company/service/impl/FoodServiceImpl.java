package com.company.service.impl;

import com.company.config.i18n.ResourceBundleService;
import com.company.config.security.details.SecurityUtil;
import com.company.dto.ApiResponse;
import com.company.dto.FoodDTO;
import com.company.entity.FoodEntity;
import com.company.repository.FoodRepository;
import com.company.service.FoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FoodServiceImpl implements FoodService {
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;
    private FoodEntity toEntity(FoodDTO dto) {
        FoodEntity entity = new FoodEntity();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setCategoryId(dto.getCategoryId());
        entity.setQuantity(dto.getQuantity());
        return entity;
    }
    private FoodDTO toDTO(FoodEntity entity) {
        FoodDTO dto = new FoodDTO();
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setCategoryId(entity.getCategoryId());
        dto.setQuantity(entity.getQuantity());
        return dto;
    }

    @Override
    public ApiResponse<?> create(FoodDTO dto) {
        FoodEntity entity = toEntity(dto);
        entity.setOwnerId(SecurityUtil.getCurrentProfileId());

        log.info("food created " + entity.getId());

        FoodEntity saved = foodRepository.save(entity);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.created", SecurityUtil.getProfileLanguage()), toDTO(saved));
    }

    @Override
    public ApiResponse<?> update(Integer id, FoodDTO dto) {
        Optional<FoodEntity> optionalFood = foodRepository.findById(id);
        if (optionalFood.isEmpty()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        FoodEntity entity = optionalFood.get();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());

        log.warn("food updated " + id);

        FoodEntity saved = foodRepository.save(entity);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.updated", SecurityUtil.getProfileLanguage()), toDTO(saved));
    }

    @Override
    public ApiResponse<?> delete(Integer id) {
        if (!foodRepository.existsById(id)) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        foodRepository.deleteById(id);
        log.warn("food deleted " + id);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.deleted", SecurityUtil.getProfileLanguage()));
    }

    @Override
    public ApiResponse<?> getById(Integer id) {
        Optional<FoodEntity> optionalFood = foodRepository.findById(id);
        if (optionalFood.isEmpty()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        FoodEntity entity = optionalFood.get();
        return new ApiResponse<>(true, toDTO(entity));
    }

    @Override
    public List<FoodDTO> getList() {
        List<FoodEntity> all = foodRepository.findAll();
        return all
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public Page<FoodDTO> paging(int page, int size) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<FoodEntity> entities = foodRepository.findAll(pageable);
        List<FoodDTO> dtos = entities
                .stream()
                .map(this::toDTO)
                .toList();
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }

    @Override
    public Page<?> pagingByCategoryId(Integer categoryId, int page, int size) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<FoodEntity> entities = foodRepository.findAllByCategoryId(categoryId, pageable);
        List<FoodDTO> dtos = entities
                .stream()
                .map(this::toDTO)
                .toList();
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }
}
