package com.jacob.inventario.utils;

import org.springframework.http.HttpStatus;

public enum EnumErrorsCodes {
    ENTITY_NOT_FOUND("El registro no fue encontrado", HttpStatus.NOT_FOUND),
    SAME_SN("Ya existe un registro con ese numero de serie", HttpStatus.CONFLICT),
    DUPLICATE_ENTITY("Ya existe un registro con los mismos datos", HttpStatus.CONFLICT),
    INVALID_INPUT("Los datos proporcionados son inválidos", HttpStatus.BAD_REQUEST),
    DATABASE_ERROR("Error al acceder a la base de datos", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED("No tiene permisos para realizar esta acción", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("Acceso denegado", HttpStatus.FORBIDDEN),
    INTERNAL_SERVER_ERROR("Ocurrió un error interno en el servidor", HttpStatus.INTERNAL_SERVER_ERROR),
    UNKNOWN_ERROR("Error desconocido", HttpStatus.INTERNAL_SERVER_ERROR);
    private final String message;
    private final HttpStatus httpStatus;

    EnumErrorsCodes(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
