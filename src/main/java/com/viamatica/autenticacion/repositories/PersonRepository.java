package com.viamatica.autenticacion.repositories;

import com.viamatica.autenticacion.entities.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends BaseRepository<Person,Long> {
}


