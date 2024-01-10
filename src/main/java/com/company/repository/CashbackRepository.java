package com.company.repository;

import com.company.entity.CashbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CashbackRepository extends JpaRepository<CashbackEntity, String> {
    Optional<CashbackEntity> findByPhone(String phone);
}
