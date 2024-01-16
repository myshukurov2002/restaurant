package com.company.entity;

import com.company.entity.base.IntegerBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "category")
public class CategoryEntity extends IntegerBaseEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "categoryEntity", fetch = FetchType.LAZY)
    private List<FoodEntity> foodEntities;

    @ManyToOne
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    private ProfileEntity profileEntity;

    @Column(name = "owner_id")
    private String ownerId;
}
