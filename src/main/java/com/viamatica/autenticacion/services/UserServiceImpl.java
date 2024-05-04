package com.viamatica.autenticacion.services;

import com.viamatica.autenticacion.dtos.ApiResponse;
import com.viamatica.autenticacion.entities.LogLogin;
import com.viamatica.autenticacion.entities.User;
import com.viamatica.autenticacion.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl extends BaseServiceImpl<User,Long> implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    LogLoginServiceImpl logLoginService;


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

    public ApiResponse<?> getLogsByUserId(Long user_id){
        ApiResponse<List<LogLogin>> response = new ApiResponse<>();
        HttpStatus status = HttpStatus.OK;
        try {
            List<LogLogin> logsList= logLoginService.getLogByUserId(user_id);
            if(!logsList.isEmpty()) {
                response.setData(logsList);
                response.setMessage("Listado de logs del usuario emitida");
            }
        }
        catch(NoSuchElementException ns){
            status = HttpStatus.NOT_FOUND;
            response.setMessage("Usuario no existe, sin logs");
        }
        catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            response.setMessage("Error al procesar la solicitud");
        }

        response.setStatusCode(status.value());
        response.setShowMessage(true);

        return response;
    }

    public ApiResponse<?> getUserByEmail(String email){
        ApiResponse<String> response = new ApiResponse<>();
        HttpStatus status = HttpStatus.OK;
        try {
            User existUser = userRepository.findUserByUserEmail(email);
            if (existUser != null) {
                String uniqueEmail = generateUniqueEmail(email);
                response.setData(uniqueEmail);
                response.setMessage("Email generado correctamente");
            } else {
                status = HttpStatus.NOT_FOUND;
                response.setMessage("No hay usuarios registrados con ese email: "+email);
            }
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            response.setMessage("Error al procesar la solicitud");
        }
        response.setStatusCode(status.value());
        response.setShowMessage(true);
        return response;
    }

    public ApiResponse<?> getUserByUserName(String username){
        ApiResponse<User> response = new ApiResponse<>();
        HttpStatus status = HttpStatus.OK;

        try {
            User existUser = userRepository.findByUsername(username).get();
            response.setData(existUser);
            response.setMessage("Usuario encontrado");
        }
        catch(NoSuchElementException ns){
            status = HttpStatus.NOT_FOUND;
            response.setMessage("Usuario no existe");
        }
        catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            response.setMessage("Error al procesar la solicitud");
        }

        response.setStatusCode(status.value());
        response.setShowMessage(true);

        return response;
    }


    private String generateUniqueEmail(String email) {
        int index = 1;
        String[] parts = email.split("@");
        String username = parts[0]; // Obtener la parte del nombre de usuario antes del "@"
        String domain = parts[1]; // Obtener la parte del dominio después del "@"
        String uniqueEmail = email;
        while (userRepository.findUserByUserEmail(uniqueEmail) != null) {
            // Agregar número antes del dominio
            uniqueEmail = username + index + "@" + domain;
            index++;
        }
        return uniqueEmail;
    }

}
