package com.viamatica.autenticacion.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.sql.Date;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "persons")
@Data
@AllArgsConstructor
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "person_id"))
public class Person extends Base {

    @NotBlank(message = "El campo personFirstName es requerido")
    @Column(name = "person_first_name",length = 60)
    private String personFirstName;

    @NotBlank(message = "El campo personLastName es requerido")
    @Column(name = "person_last_name",length = 60)
    private String personLastName;

    @Pattern(regexp = "\\d{10}", message = "El campo personIdentification debe tener una longitud de 10 dígitos")
    @Pattern(regexp = "(?!.*([0-9])\\1{3})\\d{10}", message = "El campo personIdentification no puede tener cuatro números iguales consecutivos")
    @NotBlank(message = "El campo personIdentification es requerido")
    @Column(name = "person_identification",length = 10)
    private String personIdentification;

    @NotNull(message = "El campo personBirthDate no puede ser nulo")
    @Column(name = "person_birth_date")
    @Past(message = "El campo personBirthDate debe ser una fecha en el pasado")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date personBirthDate;

}
