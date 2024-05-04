package com.viamatica.autenticacion.services;

import com.viamatica.autenticacion.entities.LogLogin;
import com.viamatica.autenticacion.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LogLoginService extends BaseService<LogLogin, Long> {
    List<LogLogin> getLogByUserId(Long user_id);
}
