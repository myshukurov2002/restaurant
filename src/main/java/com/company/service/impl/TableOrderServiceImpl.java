package com.company.service.impl;

import com.company.config.i18n.ResourceBundleService;
import com.company.config.security.details.SecurityUtil;
import com.company.dto.ApiResponse;
import com.company.dto.TableOrderDTO;
import com.company.entity.TableOrderEntity;
import com.company.repository.TableOrderRepository;
import com.company.service.TableOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TableOrderServiceImpl implements TableOrderService {
    @Autowired
    private TableOrderRepository tableOrderRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;

    public TableOrderEntity toEntity(TableOrderDTO dto) {

        TableOrderEntity entity = new TableOrderEntity();
        entity.setTableStatus(dto.getTableStatus());
        entity.setTableType(dto.getTableType());
        return entity;
    }

    public TableOrderDTO toDTO(TableOrderEntity entity) {
        TableOrderDTO dto = new TableOrderDTO();
        dto.setTableType(entity.getTableType());
        dto.setTableStatus(entity.getTableStatus());
        return dto;
    }

    @Override
    public ApiResponse<?> create(TableOrderDTO dto) {
        TableOrderEntity entity = toEntity(dto);
        entity.setOwnerId(SecurityUtil.getCurrentProfileId());

        log.info("tableOrder created " + entity.getId());

        TableOrderEntity saved = tableOrderRepository.save(entity);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.created", SecurityUtil.getProfileLanguage()), toDTO(saved));
    }

    @Override
    public ApiResponse<?> update(Integer id, TableOrderDTO dto) {
        Optional<TableOrderEntity> optionalTableOrder = tableOrderRepository.findById(id);
        if (optionalTableOrder.isEmpty()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        TableOrderEntity entity = optionalTableOrder.get();
        entity.setTableStatus(dto.getTableStatus());

        log.warn("tableOrder updated " + id);

        TableOrderEntity saved = tableOrderRepository.save(entity);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.updated", SecurityUtil.getProfileLanguage()), toDTO(saved));
    }

    @Override
    public ApiResponse<?> delete(Integer id) {
        if (!tableOrderRepository.existsById(id)) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        tableOrderRepository.deleteById(id);
        log.warn("tableOrder deleted " + id);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.deleted", SecurityUtil.getProfileLanguage()));
    }


    @Override
    public ApiResponse<?> getById(Integer id) {
        Optional<TableOrderEntity> optionalTableOrder = tableOrderRepository.findById(id);
        if (optionalTableOrder.isEmpty()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        TableOrderEntity entity = optionalTableOrder.get();
        return new ApiResponse<>(true, toDTO(entity));
    }

    @Override
    public List<TableOrderDTO> getList() {
        List<TableOrderEntity> all = tableOrderRepository.findAll();
        return all
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public Page<TableOrderDTO> paging(int page, int size) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TableOrderEntity> entities = tableOrderRepository.findAll(pageable);
        List<TableOrderDTO> dtos = entities
                .stream()
                .map(this::toDTO)
                .toList();
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }
}
