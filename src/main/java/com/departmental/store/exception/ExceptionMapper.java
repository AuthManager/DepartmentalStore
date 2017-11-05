package com.departmental.store.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionMapper {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionMapper.class);

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    ResponseEntity<Object> handleBusinessException(BusinessException e) {
        logger.warn(String.format("Failed due to BusinessException with error - [%s].", e.getMessage()));
        return new ResponseEntity<>(body(e.getError()), e.getStatus());
    }

    private Map body(String error) {
        return new HashMap() {{
            put("error", error);
        }};
    }

}
