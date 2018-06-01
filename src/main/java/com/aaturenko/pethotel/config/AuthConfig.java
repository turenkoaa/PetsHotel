package com.aaturenko.pethotel.config;

import org.springframework.stereotype.Service;

@Service
public class AuthConfig {

    private static AuthConfig instance;

    private AuthConfig() {
    }

    public static AuthConfig getInstance() {
        if (instance == null) {
            instance = new AuthConfig();
        }
        return instance;
    }
}
