package com.viamatica.autenticacion.services;

import com.viamatica.autenticacion.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends BaseService<User, Long> {
}
