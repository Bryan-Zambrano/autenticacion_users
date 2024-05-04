package com.viamatica.autenticacion.services;


import com.viamatica.autenticacion.entities.LogLogin;
import com.viamatica.autenticacion.entities.User;
import com.viamatica.autenticacion.repositories.LogLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogLoginServiceImpl extends BaseServiceImpl<LogLogin,Long> implements LogLoginService {

    @Autowired
    private LogLoginRepository logLoginRepository;

    @Override
    public List<LogLogin> getLogByUserId(Long user_id) {
        return logLoginRepository.findByLogUserId(user_id);
    }
}
