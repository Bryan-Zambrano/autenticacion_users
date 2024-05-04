package com.viamatica.autenticacion.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int statusCode;
    private T data;
    private String message;
    private boolean showMessage;
}
