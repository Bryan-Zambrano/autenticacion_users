package com.viamatica.autenticacion.controllers;

import com.viamatica.autenticacion.dtos.ApiResponse;
import com.viamatica.autenticacion.entities.Base;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.viamatica.autenticacion.services.BaseServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class BaseControllerImpl <E extends Base,S extends BaseServiceImpl<E,Long>> implements BaseController<E,Long> {

    @Autowired
    protected S servicio;




    @GetMapping("")
    public ResponseEntity<?> getAll() {
        ApiResponse<List<?>> response = new ApiResponse<>();
        HttpStatus status = HttpStatus.OK;
        try {
            List<?> data = servicio.findAll();
            response.setData(data);
            response.setMessage("Operación exitosa");
        } catch (Exception ex) {
            status = HttpStatus.NOT_FOUND;
            response.setMessage("Error, Por favor intente más tarde.");
        }
        response.setStatusCode(status.value());
        response.setShowMessage(true);
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        ApiResponse<Object> response = new ApiResponse<>();
        HttpStatus status = HttpStatus.OK;

        try {
            Object data = servicio.findById(id);
            if (data != null) {
                response.setData(data);
                response.setMessage("Operación exitosa");
            } else {
                status = HttpStatus.NOT_FOUND;
                response.setMessage("No se encontró el recurso con el ID proporcionado");
            }
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            response.setMessage("Error al procesar la solicitud. Por favor, inténtelo de nuevo más tarde.");
        }

        response.setStatusCode(status.value());
        response.setShowMessage(true);

        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(response);
    }



    @PostMapping("")
    public ResponseEntity<?> save(@Valid @RequestBody E entity, BindingResult bindingResult) {
        ApiResponse<Object> response = new ApiResponse<>();
        HttpStatus status = HttpStatus.OK;

        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                    .collect(Collectors.toList());

            status = HttpStatus.BAD_REQUEST;
            response.setData(errorMessages);
            response.setMessage("Error de validación");
        } else {
            try {
                E savedEntity = servicio.save(entity);
                if(savedEntity!=null){
                    // Aquí puedes asignar la entidad guardada si es necesario
                    response.setData(savedEntity);
                    response.setMessage("Entidad guardada correctamente");
                }
            } catch (DataIntegrityViolationException ex) {
                status = HttpStatus.BAD_REQUEST;
                String errorMessage = ex.getLocalizedMessage();
                response.setMessage(errorMessage);
            } catch (Exception ex) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                response.setMessage("Error al guardar la entidad. Por favor, inténtelo de nuevo más tarde.");
            }
        }

        response.setStatusCode(status.value());
        response.setShowMessage(true);

        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(response);
    }


//    @PutMapping("/{id}")
//    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody E entity) {
//        HttpStatus status = HttpStatus.OK;
//        ApiResponse<Object> response = new ApiResponse<>();
//
//        try {
//            Object updatedEntity = servicio.update(id, entity);
//            response.setData(updatedEntity);
//            response.setMessage("Entidad actualizada correctamente");
//        } catch (Exception ex) {
//            status = HttpStatus.BAD_REQUEST;
//            response.setMessage("Error al actualizar la entidad. Por favor, inténtelo de nuevo más tarde.");
//        }
//
//        response.setStatusCode(status.value());
//        response.setShowMessage(true);
//        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(response);
//    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        HttpStatus status = HttpStatus.OK;
        ApiResponse<Object> response = new ApiResponse<>();

        try {
            servicio.delete(id);
            response.setMessage("Entidad con identificador "+ id +" eliminado satisfactoriamente");
        } catch (Exception ex) {
            status = HttpStatus.BAD_REQUEST;
            response.setMessage("Error al eliminar la entidad. Por favor, inténtelo de nuevo más tarde.");
        }

        response.setStatusCode(status.value());
        response.setShowMessage(true);
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(response);
    }

}
