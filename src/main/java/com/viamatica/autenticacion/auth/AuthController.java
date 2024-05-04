package com.viamatica.autenticacion.auth;

import com.viamatica.autenticacion.auth.request.AuthResponse;
import com.viamatica.autenticacion.auth.request.LoginRequest;
import com.viamatica.autenticacion.auth.request.RegisterRequest;
import com.viamatica.autenticacion.entities.LogLogin;
import com.viamatica.autenticacion.entities.User;
import com.viamatica.autenticacion.repositories.LogLoginRepository;
import com.viamatica.autenticacion.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/auth")
public class AuthController {

    private final AuthService authService;

    private final LogLoginRepository logLoginRepository;

    @Autowired
    protected UserServiceImpl userServiceImpl;

    @PostMapping(value = "login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request)
    {
        try{
            User existUser = userServiceImpl.findByUsername(request.getUserName());
            if(existUser!=null){
                // Previo al login es necesario verificar si no esta bloqueado por demasiados intentos de inicio de sesión
                boolean isActivedUser= userServiceImpl.isUserActiveById(existUser.getId());
                if(isActivedUser){
                    // Inicia el proceso de login
                    AuthResponse authResponse=authService.login(request);
                    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                    // El usuario existe, es necesario registrar el inicio de sesión exitoso/ Generación del token de acceso
                    this.logLoginRepository.save(
                            LogLogin.builder()
                                    .logDate(currentTimestamp)
                                    .logStatus(true)
                                    .logUser(existUser)
                                    .build()
                    );
                    return ResponseEntity.ok(authResponse);
                }else{
                    return ResponseEntity.ok("Usuario inactivo");
                }
            }
            return ResponseEntity.notFound().build();

        } catch(Exception ex){

            try {
                User existUser = userServiceImpl.findByUsername(request.getUserName());
                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                // El usuario existe, es necesario registrar los inicio de sesión fallidos
                this.logLoginRepository.save(
                        LogLogin.builder()
                                .logDate(currentTimestamp)
                                .logStatus(false)
                                .logUser(existUser)
                                .build()
                );
                return ResponseEntity.ok("La contraseña es incorrecta");
            }
            catch(NoSuchElementException ns){
                // El usuario no existe en base
                // return ResponseEntity.notFound().build();
                return ResponseEntity.ok("Usuario no existe");
            }

        }
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(authService.register(request));
    }
}