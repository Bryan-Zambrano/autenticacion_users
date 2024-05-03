package com.viamatica.autenticacion.controllers;

import com.viamatica.autenticacion.entities.Rol;
import com.viamatica.autenticacion.services.RolServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/role")
public class RolController extends BaseControllerImpl<Rol, RolServiceImpl> {
}
