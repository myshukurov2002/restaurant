package com.company.entity;

import com.company.entity.base.IntegerBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "category_food")
public class CategoryFoodEntity extends IntegerBaseEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "categoryFoodEntity")
    private List<FoodEntity> foodEntities;
}
