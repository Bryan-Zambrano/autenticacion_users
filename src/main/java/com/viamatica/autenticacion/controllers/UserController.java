package com.viamatica.autenticacion.controllers;
import com.viamatica.autenticacion.entities.User;
import com.viamatica.autenticacion.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(path = "api/v1/user")
public class UserController extends BaseControllerImpl<User, UserServiceImpl> {

    @Autowired
    protected UserServiceImpl userService;

    @GetMapping("/userByEmail")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/userByUserName")
    public ResponseEntity<?> getUserByUserName(@RequestParam String username) {
        return ResponseEntity.ok(userService.getUserByUserName(username));
    }


    @GetMapping("/logsByUserId")
    public ResponseEntity<?> getLogsByUserId(@RequestParam Long user_id) {
        return ResponseEntity.ok(userService.getLogsByUserId(user_id));
    }

}
