package com.company.entity;

import com.company.entity.base.StringBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "order_food")
public class OrderFoodEntity extends StringBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", insertable = false, updatable = false)
    private MenuEntity menuEntity;

    @Column(name = "menu_id")
    private String menuId;

    @Column(name = "price")
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", insertable = false, updatable = false)
    private FoodEntity foodEntity;

    @Column(name = "food_id")
    private Integer foodId;
}
