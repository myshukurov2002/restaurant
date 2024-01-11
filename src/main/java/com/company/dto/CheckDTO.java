package com.company.dto;

import com.company.entity.MenuEntity;
import com.company.entity.ProfileEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckDTO {
    private String clientName;
    private String phone;
    private Double price;
    private MenuEntity menuEntity;
    private Date date;
    private ProfileEntity profileEntity;
    private String ownerId;
}
