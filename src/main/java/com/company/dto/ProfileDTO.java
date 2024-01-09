package com.company.dto;

import com.company.entity.ProfileRoleEntity;
import com.company.enums.Language;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    @NotBlank(message = "FIRSTNAME MUST BE NOT BLANK")
    @Size(min = 3, message = "FIRSTNAME MUST BE MINIMUM 3 CHARACTER")
    private String firstName;
    private String secondName;
    private String thirdName; // father's name
    private String email;
    private String phone;
    private String password;
    private LocalDate birthDate;
    private String nationality;
    private ProfileStatus status;
    private List<ProfileRoleEntity> roleEntities;
    private List<ProfileRole> roles;
    private Language lang;
    private String jwt;
}
