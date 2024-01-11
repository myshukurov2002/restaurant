package com.company.service.impl;

import com.company.config.i18n.ResourceBundleService;
import com.company.config.security.details.SecurityUtil;
import com.company.dto.ApiResponse;
import com.company.dto.MenuDTO;
import com.company.entity.MenuEntity;
import com.company.entity.OrderFoodEntity;
import com.company.entity.TableOrderEntity;
import com.company.enums.MenuStatus;
import com.company.enums.TableStatus;
import com.company.exp.AppBadRequestException;
import com.company.re.MenuRepository;
import com.company.repository.TableOrderRepository;
import com.company.service.MenuService;
import com.company.service.OrderFoodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class MenServiceImpl implements MenuService {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private TableOrderRepository tableOrderRepository;
    @Autowired
    private OrderFoodRepository orderFoodRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;

    public MenuEntity toEntity(MenuDTO dto) {
        MenuEntity entity = new MenuEntity();
        entity.setMenuStatus(dto.getMenuStatus());

        if (tableOrderRepository.existsById(dto.getTableOrderId())) {
            TableOrderEntity tableOrder = tableOrderRepository
                    .getById(dto.getTableOrderId());
            if (tableOrder.getTableStatus().equals(TableStatus.BUSY)) {
                throw new AppBadRequestException("THIS TABLE IS BUSY !!!");

            } else {
                tableOrder.setTableStatus(TableStatus.BUSY);
                tableOrderRepository.save(tableOrder);
            }
        }


        entity.setTableOrderId(dto.getTableOrderId());
        entity.setMenuStatus(MenuStatus.SENT);

        AtomicReference<Double> sum = new AtomicReference<>(0d);
        List<OrderFoodEntity> orderFoodEntities = dto.getOrderFoodEntities();
        orderFoodEntities.forEach(o -> {
            OrderFoodEntity ofe = new OrderFoodEntity();
            ofe.setFoodId(o.getFoodId());
            ofe.setMenuId(o.getMenuId());
            ofe.setPrice(o.getPrice());
            sum.updateAndGet(v -> v + ofe.getPrice());
            orderFoodRepository.save(ofe);
        });
        entity.setPrice(sum.get());

        return entity;
    }

    public MenuDTO toDTO(MenuEntity entity) {
        MenuDTO dto = new MenuDTO();
        dto.setMenuStatus(entity.getMenuStatus());
        dto.setTableOrderId(entity.getTableOrderId());
        dto.setMenuStatus(entity.getMenuStatus());
        dto.setPrice(entity.getPrice());
        return dto;
    }

    @Override
    public ApiResponse<?> create(MenuDTO dto) {
        MenuEntity entity = toEntity(dto);
        entity.setOwnerId(SecurityUtil.getCurrentProfileId());

        log.info("menu created " + entity.getId());

        MenuEntity saved = menuRepository.save(entity);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.created", SecurityUtil.getProfileLanguage()), toDTO(saved));
    }

    @Override
    public ApiResponse<?> update(String id, MenuDTO dto) {
        Optional<MenuEntity> optionalMenu = menuRepository.findById(id);
        if (optionalMenu.isEmpty()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        MenuEntity entity = optionalMenu.get();
        entity.setMenuStatus(dto.getMenuStatus());
        entity.setPrice(dto.getPrice());

        if (dto.getMenuStatus().equals(MenuStatus.EATEN)) {
            Optional<TableOrderEntity> optionalTable = tableOrderRepository
                    .findById(dto.getTableOrderId());
            if (optionalMenu.isEmpty()) {
                throw new AppBadRequestException("ITEM NOT FOUND !!!");
            }
            TableOrderEntity tableOrder = optionalTable.get();
            tableOrder.setTableStatus(TableStatus.EMPTY);
            tableOrderRepository.save(tableOrder);
        }

        log.warn("menu updated " + id);

        MenuEntity saved = menuRepository.save(entity);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.updated", SecurityUtil.getProfileLanguage()), toDTO(saved));
    }

    @Override
    public ApiResponse<?> delete(String id) {
        if (!menuRepository.existsById(id)) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        menuRepository.deleteById(id);
        log.warn("menu deleted " + id);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.deleted", SecurityUtil.getProfileLanguage()));
    }

    @Override
    public ApiResponse<?> getById(String id) {
        Optional<MenuEntity> optionalMenu = menuRepository.findById(id);
        if (optionalMenu.isEmpty()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        MenuEntity entity = optionalMenu.get();
        return new ApiResponse<>(true, toDTO(entity));
    }

    @Override
    public List<MenuDTO> getList() {
        List<MenuEntity> all = menuRepository.findAll();
        return all
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public Page<MenuDTO> paging(int page, int size) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<MenuEntity> entities = menuRepository.findAll(pageable);
        List<MenuDTO> dtos = entities
                .stream()
                .map(this::toDTO)
                .toList();
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }

}
