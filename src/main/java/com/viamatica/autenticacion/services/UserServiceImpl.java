package com.viamatica.autenticacion.services;

import com.viamatica.autenticacion.entities.User;
import com.viamatica.autenticacion.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User,Long> implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User authenticateUser(String userName, String userPassword) {
        return userRepository.findByUserNameAndUserPassword(userName, userPassword);
    }

    @Override
    public User findUserByEmail(String userEmail) {
        return userRepository.findUserByUserEmail(userEmail);
    }
}
