package com.viamatica.autenticacion.repositories;

import com.viamatica.autenticacion.entities.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends BaseRepository<User,Long> {
    User findByUsernameAndPassword(String userName, String userPassword);
    User findUserByUserEmail(String userEmail);
    Optional<User> findByUsername(String username);

}
