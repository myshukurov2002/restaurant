package com.company.service.impl;

import com.company.config.i18n.ResourceBundleService;
import com.company.config.security.details.SecurityUtil;
import com.company.dto.ApiResponse;
import com.company.dto.CheckDTO;
import com.company.entity.CashbackEntity;
import com.company.entity.CheckEntity;
import com.company.repository.MenuRepository;
import com.company.repository.CashbackRepository;
import com.company.repository.CheckRepository;
import com.company.service.CheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CheckServiceImpl implements CheckService {
    @Autowired
    private CheckRepository checkRepository;
    @Autowired
    private CashbackRepository cashbackRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;
    private CheckEntity toEntity(CheckDTO dto) {
        CheckEntity entity = new CheckEntity();
        entity.setPrice(dto.getPrice());
        entity.setClientName(dto.getClientName());
        entity.setPhone(dto.getPhone());
        return entity;
    }
    private CheckDTO toDTO(CheckEntity entity) {
        CheckDTO dto = new CheckDTO();
        dto.setPrice(entity.getPrice());
        dto.setClientName(entity.getClientName());
        dto.setPhone(entity.getPhone());
//        dto.setMenuEntity(entity.getMenuEntity());
        return dto;
    }

    @Override
    public ApiResponse<?> create(Boolean useCashback, CheckDTO dto) {
        CheckEntity entity = toEntity(dto);
        entity.setOwnerId(SecurityUtil.getCurrentProfileId());

        Optional<CashbackEntity> optionalCashback = cashbackRepository
                .findByPhone(dto.getPhone());

        CashbackEntity cashbackEntity = optionalCashback.orElseGet(CashbackEntity::new);
        if (useCashback) {
            entity.setPrice(dto.getPrice() - cashbackEntity.getPrice());
            cashbackEntity.setPrice(0.0);
        }
        cashbackEntity.setPrice(cashbackEntity.getPrice() + dto.getPrice() * 0.05); // given  5% chasback
        cashbackEntity.setPhone(dto.getPhone());
        cashbackRepository.save(cashbackEntity);

        log.info("order created " + entity.getId());

        CheckEntity saved = checkRepository.save(entity);
//
//        MenuEntity menu = menuRepository.getById(saved.getMenuEntity().getId());
//        menu.setMenuStatus(MenuStatus.DONE);
//        menuRepository.save(menu);



        return new ApiResponse<>(true, resourceBundleService.getMessage("success.created", SecurityUtil.getProfileLanguage()), toDTO(saved));
    }

    @Override
    public ApiResponse<?> update(String id, CheckDTO dto) {
        Optional<CheckEntity> optionalOrder = checkRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }

        CheckEntity entity = optionalOrder.get();
        entity.setClientName(dto.getClientName());
        entity.setPhone(dto.getPhone());

        log.warn("order updated " + id);

        CheckEntity saved = checkRepository.save(entity);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.updated", SecurityUtil.getProfileLanguage()), toDTO(saved));
    }

    @Override
    public ApiResponse<?> delete(String id) {
        if (!checkRepository.existsById(id)) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        checkRepository.deleteById(id);
        log.warn("order deleted " + id);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.deleted", SecurityUtil.getProfileLanguage()));
    }

    @Override
    public ApiResponse<?> getById(String id) {
        Optional<CheckEntity> optionalOrder = checkRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        CheckEntity entity = optionalOrder.get();
        return new ApiResponse<>(true, toDTO(entity));
    }

    @Override
    public List<CheckDTO> getList() {
        List<CheckEntity> all = checkRepository.findAll();
        return all
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public Page<CheckDTO> paging(int page, int size) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CheckEntity> entities = checkRepository.findAll(pageable);
        List<CheckDTO> dtos = entities
                .stream()
                .map(this::toDTO)
                .toList();
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }
}
