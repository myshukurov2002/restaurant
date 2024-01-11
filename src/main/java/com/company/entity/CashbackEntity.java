package com.company.entity;

import com.company.entity.base.StringBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "cashback")
public class CashbackEntity extends StringBaseEntity {

    @Column(name = "price", columnDefinition = "double default 0.0")
    private Double price = 0.0;

    @Column(name = "phone")
    private String phone;
}
