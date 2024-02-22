package com.vrpigroup.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * JwtConfig is a class that holds the jwt configuration.
 * @Author Aman Raj
 * @version 1.0
 * @since 2021-06-22
 * @apiNote This is a jwt config class
 * @Email :amanrashm@gmail.com
 */

@Component
@PropertySource("classpath:application.properties")
public class JwtConfig {

    @Value("${jwt.secret}")
    private static final String SECRET = "";

    public String getSecret() {
        return SECRET;
    }
}