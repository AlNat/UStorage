package dev.alnat.ustorage.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * См https://habr.com/ru/post/274985/
 * и  https://habr.com/ru/post/175375/
 *
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
public class EmailValidator implements ConstraintValidator<Email, String> {
    private volatile Pattern emailPattern = Pattern.compile("/.+@.+\\..+/i");

    @Override
    public void initialize(Email constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        Matcher matcher = emailPattern.matcher(value);
        return matcher.find();
    }

}