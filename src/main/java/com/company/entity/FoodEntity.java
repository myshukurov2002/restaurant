package com.company.entity;

import com.company.entity.base.IntegerBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "food")
public class FoodEntity extends IntegerBaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private Integer quantity = 1;

    @Column(name = "price")
    private Double price;

    @OneToMany(mappedBy = "foodEntity")
    private List<OrderFoodEntity> orderFoodEntityList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity categoryEntity;

    @Column(name = "category_id")
    private Integer categoryId;

    @ManyToOne
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    private ProfileEntity profileEntity;

    @Column(name = "owner_id")
    private String ownerId;
}
