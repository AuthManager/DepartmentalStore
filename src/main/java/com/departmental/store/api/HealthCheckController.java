package com.departmental.store.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/healthcheck")
public class HealthCheckController{

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> healthcheck() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
