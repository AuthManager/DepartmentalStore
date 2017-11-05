package com.departmental.store.api.auth.controller;

import com.departmental.store.api.auth.controller.dto.request.AuthRequest;
import com.departmental.store.api.auth.controller.dto.response.AuthResponse;
import com.departmental.store.api.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(path = "password", method = RequestMethod.POST)
    public ResponseEntity<?> password(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.authenticate(authRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @RequestMapping(path = "session", method = RequestMethod.GET)
    public ResponseEntity<?> session(@CookieValue(value = "AUTHORIZATION", required = false) String authcode) {
        authService.session(authcode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logout(@CookieValue(value = "AUTHORIZATION", required = false) String authcode) {
        authService.logout(authcode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
