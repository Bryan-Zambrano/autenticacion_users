package com.viamatica.autenticacion.auth;
import com.viamatica.autenticacion.auth.jwt.JwtService;
import com.viamatica.autenticacion.auth.request.AuthResponse;
import com.viamatica.autenticacion.auth.request.LoginRequest;
import com.viamatica.autenticacion.auth.request.RegisterRequest;
import com.viamatica.autenticacion.dtos.ApiResponse;
import com.viamatica.autenticacion.entities.LogLogin;
import com.viamatica.autenticacion.entities.Person;
import com.viamatica.autenticacion.entities.Role;
import com.viamatica.autenticacion.entities.User;
import com.viamatica.autenticacion.repositories.LogLoginRepository;
import com.viamatica.autenticacion.repositories.PersonRepository;
import com.viamatica.autenticacion.repositories.UserRepository;
import com.viamatica.autenticacion.services.UserServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final UserServiceImpl userServiceImpl;
    private final LogLoginRepository logLoginRepository;
    private final PersonRepository personRepository;

    public ApiResponse<?> login(LoginRequest request)  {
        // Lógica para inicio de sesión
        ApiResponse<Object> response = new ApiResponse<>();
        HttpStatus status = HttpStatus.OK;
        User existUser = new User();

        try{
            existUser = userServiceImpl.findByUsername(request.getUserName());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getUserPassword()));
            UserDetails user= userRepository.findByUsername(request.getUserName()).orElseThrow();
            String token=jwtService.getToken(user);
            response.setMessage("Token de acceso generado satisfactoriamente");
            response.setData(AuthResponse.builder()
                    .token(token)
                    .build());
            saveLog(existUser,true); // Registramos el acceso correcto a la base
        }catch (NoSuchElementException ex){
            status = HttpStatus.NOT_FOUND;
            response.setMessage("Usuario no existe");
        } catch(BadCredentialsException ex ){
            status = HttpStatus.UNAUTHORIZED;
            response.setMessage("Credenciales Incorrectas");

            saveLog(existUser,false); // Registramos el fail al intentar iniciar sesión
        } catch(Exception ex ){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            response.setMessage(ex.getMessage());
        }
        response.setStatusCode(status.value());
        response.setShowMessage(true);

        return response;
    }

    @Transactional
    public ApiResponse<?> register(RegisterRequest request) {
        ApiResponse<Object> response = new ApiResponse<>();
        HttpStatus status = HttpStatus.OK;
        try{
            User user = User.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode( request.getPassword()))
                    .userEmail(request.getUserEmail())
                    .userIsActive(request.getUserIsActive())
                    .statusUser(request.getStatusUser())
                    .userPerson(request.getUserPerson())
                    .role(Role.USER)
                    .build();

            if(request.getUserPerson().getId() != null){
                // Ha proporcionado un identificador de usuario
                response.setMessage("Usuario registrado satisfactoriamente");
                response.setData(userRepository.save(user));
            }
            else{
                Person person=personRepository.save(request.getUserPerson()); // Registramos la persona
                user.setUserPerson(person); // Seteamos el nuevo usuario
                response.setData(userRepository.save(user));
                response.setMessage("Persona y Usuario registrados satisfactoriamente");
            }

        } catch(Exception ex){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            response.setMessage(ex.getMessage());
        }
        response.setStatusCode(status.value());
        response.setShowMessage(true);
        return response;
    }

    public void saveLog(User user,boolean status){
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        this.logLoginRepository.save(
                LogLogin.builder()
                        .logDate(currentTimestamp)
                        .logStatus(status)
                        .logUser(user)
                        .build()
        );
    }

}
