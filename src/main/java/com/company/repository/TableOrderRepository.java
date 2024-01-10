package com.company.repository;

import com.company.entity.TableOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableOrderRepository extends JpaRepository<TableOrderEntity, Integer> {
}
