package com.viamatica.autenticacion.services;

import com.viamatica.autenticacion.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends BaseService<User, Long> {
    public User authenticateUser(String userName, String userPassword);
    public User findUserByEmail(String userEmail);
    public User findByUsername(String username);
    boolean isUserActiveById(Long userId);
}
