package com.company.dto.auth;

import com.company.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationDTO {
    @NotBlank(message = "FIRST NAME SHOULD NOT BE BLANK!!!")
    private String firstName;

    @NotBlank(message = "SECOND NAME SHOULD NOT BE BLANK!!!")
    private String secondName;

    @NotBlank(message = "THIRD NAME SHOULD NOT BE BLANK!!!")
    private String thirdName; // father's name

    @Email(message = "{email.not-valid}")
    private String email;

    @NotBlank(message = "PHONE SHOULD NOT BE BLANK!!!")
    @Pattern(regexp = "\\+998[1-9][0-9]{9}", message = "INVALID PHONE FORMAT!!!")
    private String phone;

    @NotNull(message = "PASSWORD SHOULD NOT BE NULL!!!")
    @NotBlank(message = "PASSWORD SHOULD NOT BE BLANK!!!")
    private String password;

    @NotNull(message = "BIRTH DATE SHOULD NOT BE NULL!!!")
    private LocalDate birthDate;

    private String nationality;
    private ProfileStatus status;
}

