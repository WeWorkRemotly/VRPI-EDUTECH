package com.vrpigroup.users.service;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
@Component
public class GmailLogin {

    private static final String CLIENT_ID = "your-client-id";
    private static final String CLIENT_SECRET = "your-client-secret";
    private static final String REDIRECT_URI = "your-redirect-uri";
    private static final String SCOPE = "https://www.googleapis.com/auth/gmail.readonly";
/*
    public static String getAuthorizationUrl() {
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                CLIENT_ID,
                CLIENT_SECRET,
                Arrays.asList(SCOPE))
                .build();

        GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI);

        return url.build();
    }

    public static GoogleCredential getAccessToken(String code) throws IOException {
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                CLIENT_ID,
                CLIENT_SECRET,
                Arrays.asList(SCOPE))
                .build();

        GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();

        return flow.createAndStoreCredential(response, null);
    }*/
}
