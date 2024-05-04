package com.viamatica.autenticacion.repositories;

import com.viamatica.autenticacion.entities.LogLogin;
import com.viamatica.autenticacion.entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogLoginRepository extends BaseRepository<LogLogin,Long> {
    List<LogLogin> findByLogUserId(Long userId);
}
