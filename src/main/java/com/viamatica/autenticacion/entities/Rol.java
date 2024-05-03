package com.viamatica.autenticacion.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "rol_id"))
public class Rol extends Base {
    @Column(name = "rol_name",length = 50)
    private String rolName;

    @ManyToMany
    @JoinTable(
            name = "rol_option",
            joinColumns = @JoinColumn(name = "rol_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    private List<Option> optionList = new ArrayList<>();

    @ManyToMany(mappedBy = "rolList")
    private List<User> userList = new ArrayList<>();
}
