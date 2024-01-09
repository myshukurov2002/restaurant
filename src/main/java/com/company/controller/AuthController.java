package com.company.controller;

import com.company.dto.ApiResponse;
import com.company.dto.auth.AuthDTO;
import com.company.dto.auth.RegistrationDTO;
import com.company.enums.Language;
import com.company.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody AuthDTO auth,
                                                @RequestParam(defaultValue = "en") Language lang) {
        return ResponseEntity.ok(authService.login(auth, lang));
    }

    @PostMapping("registration")
    public ResponseEntity<ApiResponse<?>> registration(@Valid @RequestBody RegistrationDTO reg,
                                                       @RequestParam(defaultValue = "en") Language lang) {
        return ResponseEntity.ok(authService.registration(reg, lang));
    }
}
