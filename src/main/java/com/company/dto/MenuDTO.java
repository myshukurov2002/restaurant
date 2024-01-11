package com.company.dto;

import com.company.entity.CheckEntity;
import com.company.entity.OrderFoodEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.TableOrderEntity;
import com.company.enums.MenuStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuDTO {
    private List<OrderFoodEntity> orderFoodEntities;
    private MenuStatus menuStatus;
    private Double price;
    private CheckEntity checkEntity;
    private TableOrderEntity tableOrderEntity;
    private Integer tableOrderId;
    private ProfileEntity profileEntity;
    private String ownerId;
}
