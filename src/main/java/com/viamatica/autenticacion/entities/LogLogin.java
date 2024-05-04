package com.viamatica.autenticacion.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "log")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@AttributeOverride(name = "id", column = @Column(name = "log_id"))
public class LogLogin extends Base {


    @Column(name = "log_date")
    private Timestamp logDate;

    @Column(name = "log_status")
    private Boolean logStatus;

    @ManyToOne()
    @JoinColumn(name = "fk_user_id")
    private User logUser;
}
