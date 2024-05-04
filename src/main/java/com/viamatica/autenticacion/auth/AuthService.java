package com.viamatica.autenticacion.auth;
import com.viamatica.autenticacion.auth.jwt.JwtService;
import com.viamatica.autenticacion.auth.request.AuthResponse;
import com.viamatica.autenticacion.auth.request.LoginRequest;
import com.viamatica.autenticacion.auth.request.RegisterRequest;
import com.viamatica.autenticacion.entities.Role;
import com.viamatica.autenticacion.entities.User;
import com.viamatica.autenticacion.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getUserPassword()));
        UserDetails user= userRepository.findByUsername(request.getUserName()).orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();

    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode( request.getPassword()))
                .userEmail(request.getUserEmail())
                .userIsActive(request.getUserIsActive())
                .statusUser(request.getStatusUser())
                .userPerson(request.getUserPerson())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();

    }

}
