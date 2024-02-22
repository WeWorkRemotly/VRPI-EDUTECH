package com.vrpigroup.users.service;
import org.springframework.stereotype.Component;
import java.util.Random;

/**
 * OtpUtil is a class that generates a random 6 digit OTP.
 * @Author Aman Raj
 * @version 1.0
 * @since 2021-06-22
 * @apiNote This is a otp util class
 * @Conact Us : amanrashm@gmail.com
 */

@Component
public class OtpUtil {

    public String generateOtp() {
        Random random = new Random();
        int randomNumber = random.nextInt(999999);
        String output = Integer.toString(randomNumber);
        while (output.length() < 6) {
            output = "0" + output;
        }
        return output;
    }
}