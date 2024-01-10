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

    @OneToMany(mappedBy = "orderFoodEntity", fetch = FetchType.EAGER)
    private List<OrderFoodEntity> orderFoodEntities;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MenuStatus menuStatus;

    @Column(name = "price")
    private Double price = 0d;

    @OneToOne
    private OrderEntity orderEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_order_id", insertable = false, updatable = false)
    private TableOrderEntity tableOrderEntity;

    @Column(name = "table_order_id")
    private Integer tableOrderId;

    @ManyToOne
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    private ProfileEntity profileEntity;

    @Column(name = "owner_id")
    private String ownerId;
}
