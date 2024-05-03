package com.viamatica.autenticacion.controllers;

import com.viamatica.autenticacion.entities.Option;
import com.viamatica.autenticacion.services.OptionServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/option")
public class OptionController extends BaseControllerImpl<Option, OptionServiceImpl> {
}
