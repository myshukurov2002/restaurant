package com.company.dto;

import com.company.entity.CategoryEntity;
import com.company.entity.MenuEntity;
import com.company.entity.ProfileEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FoodDTO {
    private String name;
    private Integer quantity;
    private Double price;
    private CategoryEntity categoryEntity;
    private Integer categoryId;
    private MenuEntity menuEntity;
    private String menuId;
    private ProfileEntity profileEntity;
    private String ownerId;
}
