package com.departmental.store.exception;

import org.springframework.http.HttpStatus;

public class InvalidSessionException extends BusinessException {

    public InvalidSessionException() {
        super(HttpStatus.BAD_REQUEST, "invalid_session");
    }

}
