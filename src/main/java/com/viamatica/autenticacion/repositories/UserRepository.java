package com.viamatica.autenticacion.repositories;

import com.viamatica.autenticacion.entities.User;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends BaseRepository<User,Long> {
    User findByUserNameAndUserPassword(String userName, String userPassword);
    User findUserByUserEmail(String userEmail);
}
