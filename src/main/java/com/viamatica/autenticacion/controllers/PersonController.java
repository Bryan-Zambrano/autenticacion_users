package com.viamatica.autenticacion.controllers;

import com.viamatica.autenticacion.entities.Person;
import com.viamatica.autenticacion.services.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/person")
public class PersonController extends BaseControllerImpl<Person, PersonServiceImpl> {

    @Autowired
    protected PersonServiceImpl personService;

}