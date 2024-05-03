package com.viamatica.autenticacion.repositories;

import com.viamatica.autenticacion.entities.Session;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository  extends BaseRepository<Session,Long> {
}
