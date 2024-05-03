package com.viamatica.autenticacion.services;

import com.viamatica.autenticacion.entities.Person;
import org.springframework.stereotype.Service;

@Service
public interface PersonService extends BaseService<Person, Long> {
}
