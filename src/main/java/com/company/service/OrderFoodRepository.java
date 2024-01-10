package com.company.service;

import com.company.entity.OrderFoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderFoodRepository extends JpaRepository<OrderFoodEntity, String> {
}
