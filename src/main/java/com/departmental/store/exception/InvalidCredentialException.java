package com.departmental.store.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialException extends BusinessException{

    public InvalidCredentialException() {
        super(HttpStatus.BAD_REQUEST, "invalid_credentials");
    }

}
