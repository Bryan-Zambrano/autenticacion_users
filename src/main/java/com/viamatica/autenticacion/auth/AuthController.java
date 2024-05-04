package com.viamatica.autenticacion.auth;

import com.viamatica.autenticacion.auth.request.AuthResponse;
import com.viamatica.autenticacion.auth.request.LoginRequest;
import com.viamatica.autenticacion.auth.request.RegisterRequest;
import com.viamatica.autenticacion.entities.LogLogin;
import com.viamatica.autenticacion.entities.User;
import com.viamatica.autenticacion.repositories.LogLoginRepository;
import com.viamatica.autenticacion.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    protected UserServiceImpl userServiceImpl;

    @PostMapping(value = "login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(authService.register(request));
    }
}