package com.company.entity;

import com.company.entity.base.IntegerBaseEntity;
import com.company.enums.TableStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "table_order")
public class TableOrderEntity extends IntegerBaseEntity {

    @OneToMany(mappedBy = "tableOrderEntity")
    private List<MenuEntity> menuEntities;

    @Column(name = "table_type")// how many people can sit
    private Integer tableType = 2; //default

    @Column(name = "table_status")
    @Enumerated(EnumType.STRING)
    private TableStatus tableStatus;
}
