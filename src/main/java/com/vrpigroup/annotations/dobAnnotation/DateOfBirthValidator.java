package com.vrpigroup.annotations.dobAnnotation;

import com.vrpigroup.annotations.passwordAnnotation.PasswordValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author AMAN RAJ
 * @version 1.0
 * @since 2021-06-22
 * @apiNote This is a custom annotation for date of birth validation
 * @Conact Us : amanrashm@gmail.com
 * @see ValidDateOfBirth
 */

public class DateOfBirthValidator implements ConstraintValidator<ValidDateOfBirth, String> {
    private final List<String> acceptedFormats = new ArrayList<>();

    @Override
    public void initialize(ValidDateOfBirth constraintAnnotation) {
        acceptedFormats.add("yyyy-MM-dd");
        acceptedFormats.add("yyyy/MM/dd");
        acceptedFormats.add("dd-MM-yyyy");
        acceptedFormats.add("dd/MM/yyyy");
    }

    @Override
    public boolean isValid(String dateOfBirth, ConstraintValidatorContext context) {
        if (dateOfBirth == null || dateOfBirth.trim().isEmpty()) {
            return false;
        }
        for (String format : acceptedFormats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                LocalDate dob = LocalDate.parse(dateOfBirth, formatter);
                LocalDate currentDate = LocalDate.now();
                return dob.isBefore(currentDate.minusYears(16));
            } catch (DateTimeParseException ignored) {
            }
        }
        return false;
    }
}