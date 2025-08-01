package com.anamariafelix.matematico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadResquetException extends RuntimeException {//exception para o upload e dwonload de arquivos

    public BadResquetException(String msg) {
        super(msg);
    }

    public BadResquetException(String message, Throwable cause) {
        super(message, cause);
    }
}
