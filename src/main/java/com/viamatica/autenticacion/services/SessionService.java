package com.viamatica.autenticacion.services;

import com.viamatica.autenticacion.entities.Session;
import org.springframework.stereotype.Service;

@Service
public interface SessionService extends BaseService<Session, Long> {
}
