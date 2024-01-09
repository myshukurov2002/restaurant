package com.company.entity;

import com.company.entity.base.StringBaseEntity;
import com.company.enums.MenuStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "menu")
public class MenuEntity extends StringBaseEntity {

    @OneToMany(mappedBy = "menuEntity", fetch = FetchType.EAGER)
    List<FoodEntity> foodEntities;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MenuStatus menuStatus;

    @Column(name = "price")
    private Double price;

    @OneToOne
    private OrderEntity orderEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "table_order_id", insertable = false, updatable = false)
    private TableOrderEntity tableOrderEntity;

    @Column(name = "table_order_id")
    private Integer tableOrderId;
}
