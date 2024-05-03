package com.viamatica.autenticacion.controllers;

import java.io.Serializable;

import com.viamatica.autenticacion.entities.Base;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


public interface BaseController<E extends Base,ID extends Serializable> {

    public ResponseEntity<?> getAll();
    public ResponseEntity<?> getOne(@PathVariable ID id);
    public ResponseEntity<?> save(@RequestBody E entity, BindingResult bindingResult);
    public ResponseEntity<?> update(@PathVariable ID id,@RequestBody E entity);
    public ResponseEntity<?> delete(@PathVariable ID id);

}

