package com.company.service;

import com.company.dto.ApiResponse;
import com.company.dto.auth.AuthDTO;
import com.company.dto.auth.JwtDTO;
import com.company.dto.auth.RegistrationDTO;
import com.company.enums.Language;

public interface AuthService {
    ApiResponse<?> login(AuthDTO auth, Language lang);

    ApiResponse<?> registration(RegistrationDTO reg, Language lang);

    ApiResponse<?> updateById(String id, JwtDTO dto);
}
