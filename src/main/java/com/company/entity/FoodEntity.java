package com.company.entity;

import com.company.entity.base.IntegerBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "food")
public class FoodEntity extends IntegerBaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity categoryEntity;

    @Column(name = "category_id")
    private Integer categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", insertable = false, updatable = false)
    private MenuEntity menuEntity;

    @Column(name = "menu_id")
    private String menuId;

    @ManyToOne
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    private ProfileEntity profileEntity;

    @Column(name = "owner_id")
    private String ownerId;
}
