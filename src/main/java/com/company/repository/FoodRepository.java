package com.company.repository;

import com.company.entity.FoodEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<FoodEntity, Integer> {
    Page<FoodEntity> findAllByCategoryId(Integer categoryId, Pageable pageable);
}
