package com.viamatica.autenticacion.controllers;
import com.viamatica.autenticacion.auth.request.LoginRequest;
import com.viamatica.autenticacion.dtos.ApiResponse;
import com.viamatica.autenticacion.entities.User;
import com.viamatica.autenticacion.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/user")
public class UserController extends BaseControllerImpl<User, UserServiceImpl> {

    @Autowired
    protected UserServiceImpl userService;

    @PostMapping("/logiNotToken")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        ApiResponse<User> response = new ApiResponse<>();
        HttpStatus status = HttpStatus.OK;

        try {
            User authenticatedUser = userService.authenticateUser(request.getUserName(), request.getUserPassword());
            if (authenticatedUser != null) {
                response.setData(authenticatedUser);
                response.setMessage("Inicio de sesión exitoso");
            } else {
                status = HttpStatus.UNAUTHORIZED;
                response.setMessage("Credenciales incorrectas");
            }
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            response.setMessage("Error al procesar la solicitud de inicio de sesión");
        }

        response.setStatusCode(status.value());
        response.setShowMessage(true);

        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(response);
    }


    @GetMapping("/userByEmail")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
        ApiResponse<String> response = new ApiResponse<>();
        HttpStatus status = HttpStatus.OK;

        try {
            User existUser = userService.findUserByEmail(email);
            if (existUser != null) {
                String uniqueEmail = generateUniqueEmail(email);
                response.setData(uniqueEmail);
                response.setMessage("Email generado correctamente");
            } else {
                status = HttpStatus.NOT_FOUND;
                response.setMessage("Usuario no existe");
            }
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            response.setMessage("Error al procesar la solicitud");
        }

        response.setStatusCode(status.value());
        response.setShowMessage(true);

        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(response);
    }


    private String generateUniqueEmail(String email) {
        int index = 1;
        String[] parts = email.split("@");
        String username = parts[0]; // Obtener la parte del nombre de usuario antes del "@"
        String domain = parts[1]; // Obtener la parte del dominio después del "@"

        String uniqueEmail = email;

        while (userService.findUserByEmail(uniqueEmail) != null) {
            // Agregar número antes del dominio
            uniqueEmail = username + index + "@" + domain;
            index++;
        }

        return uniqueEmail;
    }



}
