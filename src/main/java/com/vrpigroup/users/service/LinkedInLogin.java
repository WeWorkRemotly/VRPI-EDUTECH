package com.vrpigroup.users.service;

import java.io.IOException;
import java.util.Scanner;

public class LinkedInLogin {

    private static final String CLIENT_ID = "your-client-id";
    private static final String CLIENT_SECRET = "your-client-secret";
    private static final String REDIRECT_URI = "your-redirect-uri";
    private static final String SCOPE = "r_liteprofile r_emailaddress";
/*
    public static String getAuthorizationUrl() {
        OAuth20Service service = new ServiceBuilder(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .defaultScope(SCOPE)
                .callback(REDIRECT_URI)
                .build(LinkedInApi20.instance());

        return service.getAuthorizationUrl();
    }

    public static OAuth2AccessToken getAccessToken(String code) throws IOException {
        OAuth20Service service = new ServiceBuilder(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .defaultScope(SCOPE)
                .callback(REDIRECT_URI)
                .build(LinkedInApi20.instance());

        return service.getAccessToken(code);
    }*/
}
