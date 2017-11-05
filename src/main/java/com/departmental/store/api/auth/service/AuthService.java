package com.departmental.store.api.auth.service;

import com.departmental.store.api.auth.controller.dto.request.AuthRequest;
import com.departmental.store.api.auth.controller.dto.response.AuthResponse;
import com.departmental.store.exception.InvalidCredentialException;
import com.departmental.store.exception.InvalidSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {

    private Map<String, String> memoryCache;

    @Autowired
    public AuthService() {
        memoryCache = new ConcurrentHashMap<>();
    }

    public AuthResponse authenticate(AuthRequest authRequest) {
        if ("admin@gmail.com".equalsIgnoreCase(authRequest.getUsername())
                && "test1234".equals(authRequest.getPassword())) {
            String authcode = UUID.randomUUID().toString();
            memoryCache.put(authcode, authRequest.getUsername());
            return new AuthResponse(authcode, "ADMIN");
        }
        throw new InvalidCredentialException();
    }

    public void session(String authcode) {
        if (authcode != null && memoryCache.containsKey(authcode)) {
            return;
        }
        throw new InvalidSessionException();
    }

    public void logout(String authcode) {
        memoryCache.remove(authcode);
    }
}
