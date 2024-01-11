package com.company.repository;

import com.company.entity.CheckEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckRepository extends JpaRepository<CheckEntity, String> {
}
