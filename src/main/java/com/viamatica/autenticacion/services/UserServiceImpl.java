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
        return userRepository.findByUsernameAndPassword(userName, userPassword);
    }

    @Override
    public User findUserByEmail(String userEmail) {
        return userRepository.findUserByUserEmail(userEmail);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public boolean isUserActiveById(Long userId) {
        return userRepository.existsByIdAndStatusUser(userId, true);
    }
}
