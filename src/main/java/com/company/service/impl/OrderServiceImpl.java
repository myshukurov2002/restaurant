package com.company.service.impl;

import com.company.config.i18n.ResourceBundleService;
import com.company.config.security.details.SecurityUtil;
import com.company.dto.ApiResponse;
import com.company.dto.OrderDTO;
import com.company.entity.CashbackEntity;
import com.company.entity.OrderEntity;
import com.company.repository.CashbackRepository;
import com.company.repository.OrderRepository;
import com.company.service.OrderService;
import com.company.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CashbackRepository cashbackRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;
    public OrderEntity toEntity(OrderDTO dto) {
        OrderEntity entity = new OrderEntity();
        entity.setPrice(dto.getPrice());
        entity.setClientName(dto.getClientName());
        entity.setPhone(dto.getPhone());
        return entity;
    }
    public OrderDTO toDTO(OrderEntity entity) {
        OrderDTO dto = new OrderDTO();
        dto.setPrice(entity.getPrice());
        dto.setClientName(entity.getClientName());
        dto.setPhone(entity.getPhone());
        return dto;
    }

    @Override
    public ApiResponse<?> create(OrderDTO dto) {
        OrderEntity entity = toEntity(dto);
        entity.setOwnerId(SecurityUtil.getCurrentProfileId());

        Optional<CashbackEntity> optionalCashback = cashbackRepository
                .findByPhone(dto.getPhone());

        CashbackEntity cashbackEntity = optionalCashback.orElseGet(CashbackEntity::new);
        cashbackEntity.setPrice(cashbackEntity.getPrice() + dto.getPrice() * 0.1);
        cashbackRepository.save(cashbackEntity);

        log.info("order created " + entity.getId());

        OrderEntity saved = orderRepository.save(entity);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.created", SecurityUtil.getProfileLanguage()), toDTO(saved));
    }

    @Override
    public ApiResponse<?> update(String id, OrderDTO dto) {
        Optional<OrderEntity> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }

        OrderEntity entity = optionalOrder.get();
        entity.setClientName(dto.getClientName());
        entity.setPhone(dto.getPhone());

        log.warn("order updated " + id);

        OrderEntity saved = orderRepository.save(entity);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.updated", SecurityUtil.getProfileLanguage()), toDTO(saved));
    }

    @Override
    public ApiResponse<?> delete(String id) {
        if (!orderRepository.existsById(id)) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        orderRepository.deleteById(id);
        log.warn("order deleted " + id);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.deleted", SecurityUtil.getProfileLanguage()));
    }

    @Override
    public ApiResponse<?> getById(String id) {
        Optional<OrderEntity> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        OrderEntity entity = optionalOrder.get();
        return new ApiResponse<>(true, toDTO(entity));
    }

    @Override
    public List<OrderDTO> getList() {
        List<OrderEntity> all = orderRepository.findAll();
        return all
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public Page<OrderDTO> paging(int page, int size) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<OrderEntity> entities = orderRepository.findAll(pageable);
        List<OrderDTO> dtos = entities
                .stream()
                .map(this::toDTO)
                .toList();
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }
}
