package com.viamatica.autenticacion.controllers;

import com.viamatica.autenticacion.entities.Base;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.viamatica.autenticacion.services.BaseServiceImpl;
import org.springframework.validation.FieldError;
import jakarta.validation.ConstraintViolation;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class BaseControllerImpl <E extends Base,S extends BaseServiceImpl<E,Long>> implements BaseController<E,Long> {

    @Autowired
    protected S servicio;


    @GetMapping("")
    public ResponseEntity<?> getAll(){
        try {
            //List<Persona> personas = servicio.findAll();
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(servicio.findAll());

        }catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error, Por favor intente mas tarde..\" }");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){

        try {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(servicio.findById(id));

        }catch(Exception ex) {
            System.out.println(ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error, Por favor intente mas tarde..\" }");
        }
    }


    @PostMapping("")
    public ResponseEntity<?> save(@Valid @RequestBody E entity, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);

        }
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.save(entity));
        }
        catch (DataIntegrityViolationException ex ){
            String errorMessage = ex.getLocalizedMessage();
            //String detailMessage = errorMessage.replaceAll(".*Detail: (.*?)] .*", "$1");
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error al guardar, Por favor intente mas tarde..\" }");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody E entity){

        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.update(id,entity));

        }catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde..\" }");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(servicio.delete(id));

        }catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde..\" }");
        }
    }
}
