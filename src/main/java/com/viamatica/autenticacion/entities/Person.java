package com.viamatica.autenticacion.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.sql.Date;
import jakarta.validation.constraints.*;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "persons")
@Data
@AllArgsConstructor
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "person_id"))
public class Person extends Base {


    @Column(name = "person_first_name",length = 60)
    private String personFirstName;

    @Column(name = "person_last_name",length = 60)
    private String personLastName;

    @Pattern(regexp = "\\d{10}", message = "Debe tener una longitud de 10 dígitos")
    @Pattern(regexp = "(?!.*([0-9])\\1{3})\\d{10}", message = "No puede tener cuatro números iguales consecutivos")
    @Column(name = "person_identification",length = 10)
    private String personIdentification;

    @Column(name = "person_birth_date")
    private Date personBirthDate;

}
