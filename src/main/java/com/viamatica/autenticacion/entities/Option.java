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
@Table(name = "options")
@Data
@AllArgsConstructor
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "option_id"))
public class Option extends Base {
    @Column(name = "option_name",length = 50)
    private String optionName;

    @ManyToMany(mappedBy = "optionList")
    private List<Rol> roleList = new ArrayList<>();
}
