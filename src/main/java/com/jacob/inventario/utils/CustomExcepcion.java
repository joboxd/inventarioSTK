package com.jacob.inventario.utils;

import org.springframework.http.HttpStatus;

public class CustomExcepcion extends RuntimeException {
    private final EnumErrorsCodes errorCode;
    private final String message;

    public CustomExcepcion(EnumErrorsCodes errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
    
    public CustomExcepcion(EnumErrorsCodes errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }

    public EnumErrorsCodes getError() {
        return errorCode;
    }
}
