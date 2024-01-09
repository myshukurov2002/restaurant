package com.company.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthDTO {
    @NotNull(message = "PHONE NUMBER SHOULD BE NOT NULL!!!")
    @NotBlank(message = "PHONE NUMBER SHOULD BE NOT BLANK!!!")
    private String phone;
    @NotNull(message = "PASSWORD SHOULD BE NOT NULL!!!")
    @NotBlank(message = "PASSWORD SHOULD BE NOT BLANK!!!")
    @Size(min = 6, message = "PASSWORD SHOULD BE MINIMUM 6 CHARACTER!!!")
    private String password;

}
