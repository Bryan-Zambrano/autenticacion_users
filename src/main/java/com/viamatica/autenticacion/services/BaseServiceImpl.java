package com.viamatica.autenticacion.services;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.viamatica.autenticacion.repositories.BaseRepository;
import com.viamatica.autenticacion.entities.Base;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;

public abstract class BaseServiceImpl <E extends Base, ID extends Serializable> implements BaseService<E,ID> {

    @Autowired
    protected BaseRepository<E,ID> baseRepository;

    @Transactional
    public List<E> findAll() throws Exception {
        try {
            return baseRepository.findAll();
        }catch(Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }


    @Transactional
    public E findById(ID id) throws Exception {
        try {
            Optional<E> entityOptional= baseRepository.findById(id);
            return entityOptional.get();
        }catch(Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    public E save(E entity) throws Exception {
        entity=baseRepository.save(entity);
        return entity;
    }

    @Transactional
    public E update(ID id, E entity) throws Exception {
        try {
            Optional<E> entityOptional= baseRepository.findById(id);
            E entityUpdate =entityOptional.get();
            entityUpdate= baseRepository.save(entity);
            return entityUpdate;

        }catch(Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }


    @Transactional
    public boolean delete(ID id) throws Exception {
        try {

            if(baseRepository.existsById(id)) {
                baseRepository.deleteById(id);
                return true;
            }else {
                throw new Exception();
            }

        }catch(Exception ex) {
            throw new Exception(ex.getMessage());

        }
    }
}

