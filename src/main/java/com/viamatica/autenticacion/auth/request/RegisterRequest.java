package com.viamatica.autenticacion.auth.request;

import com.viamatica.autenticacion.entities.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String username;
    String password;
    String userEmail;
    Boolean userIsActive;
    Boolean statusUser;
    Person userPerson;
}
