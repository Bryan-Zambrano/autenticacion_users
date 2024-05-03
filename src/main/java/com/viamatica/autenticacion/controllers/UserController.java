package com.viamatica.autenticacion.controllers;
import com.viamatica.autenticacion.entities.User;
import com.viamatica.autenticacion.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/user")
public class UserController extends BaseControllerImpl<User, UserServiceImpl> {

    @Autowired
    protected UserServiceImpl UserService;

}
