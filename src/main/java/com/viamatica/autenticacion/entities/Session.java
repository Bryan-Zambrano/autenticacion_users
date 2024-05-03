package com.viamatica.autenticacion.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sessions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "session_id"))
public class Session extends Base {

    @Column(name = "session_connection_start")
    private Date sessionConnectionStart;

    @Column(name = "session_connection_end")
    private Date sessionConnectionEnd;

    @ManyToOne()
    @JoinColumn(name = "fk_user_id")
    private User sessionUser;
}
