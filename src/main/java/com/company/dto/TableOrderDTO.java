package com.company.dto;

import com.company.entity.MenuEntity;
import com.company.enums.TableStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableOrderDTO {
    private List<MenuEntity> menuEntities;
    private Integer tableType;
    private TableStatus tableStatus;
}
