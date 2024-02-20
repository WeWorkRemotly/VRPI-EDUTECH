package com.vrpigroup.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class JwtConfig {

    @Value("${jwt.secret}")
    private static final String SECRET = "";

    public String getSecret() {
        return SECRET;
    }
}