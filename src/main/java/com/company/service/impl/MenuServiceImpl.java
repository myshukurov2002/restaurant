package com.company.service.impl;

import com.company.config.i18n.ResourceBundleService;
import com.company.config.security.details.SecurityUtil;
import com.company.dto.ApiResponse;
import com.company.dto.MenuDTO;
import com.company.entity.FoodEntity;
import com.company.entity.MenuEntity;
import com.company.entity.OrderFoodEntity;
import com.company.entity.TableOrderEntity;
import com.company.enums.MenuStatus;
import com.company.enums.TableStatus;
import com.company.exp.AppBadRequestException;
import com.company.repository.FoodRepository;
import com.company.repository.MenuRepository;
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
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private TableOrderRepository tableOrderRepository;
    @Autowired
    private OrderFoodRepository orderFoodRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;

    private MenuDTO toDTO(MenuEntity entity) {
        MenuDTO dto = new MenuDTO();
        dto.setMenuStatus(entity.getMenuStatus());
        dto.setTableOrderId(entity.getTableOrderId());
        dto.setMenuStatus(entity.getMenuStatus());
        dto.setPrice(entity.getPrice());
        return dto;
    }

    @Override
    public ApiResponse<?> create(MenuDTO dto) {
        MenuEntity entity = new MenuEntity();
        entity.setOwnerId(SecurityUtil.getCurrentProfileId());

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
        MenuEntity saved = menuRepository.save(entity);

        AtomicReference<Double> sum = new AtomicReference<>(0d);
        List<OrderFoodEntity> orderFoodEntities = dto.getOrderFoodEntities();
        orderFoodEntities.forEach(o -> {
            OrderFoodEntity ofe = new OrderFoodEntity();
            Optional<FoodEntity> optionalFood = foodRepository.findById(o.getFoodId());
            if (optionalFood.isEmpty()) {
                throw new AppBadRequestException("FOOD NOT FOUND !!!");
            }
            FoodEntity foodEntity = optionalFood.get();
            ofe.setFoodId(o.getFoodId());
            ofe.setMenuId(entity.getId());
            ofe.setPrice(foodEntity.getPrice());
            sum.updateAndGet(v -> v + foodEntity.getPrice());
            orderFoodRepository.save(ofe);
        });
        saved.setPrice(sum.get());
        menuRepository.save(saved);

        log.info("menu created " + entity.getId());

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

        if (dto.getMenuStatus().equals(MenuStatus.EATEN) ||
            dto.getMenuStatus().equals(MenuStatus.CANCELED)) {
            Optional<TableOrderEntity> optionalTable = tableOrderRepository
                    .findById(entity.getTableOrderId());

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
