package dev.alnat.ustorage.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Аннотация для валидация E-mail адреса
 *
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface Email {

    /**
     * @return Сообщение об ошибке
     */
    String message() default "Email некорректный";

    /**
     * @return the groups the constraint belongs to
     */
    Class<?>[] groups() default { };

    /**
     * @return the payload associated to the constraint
     */
    Class<? extends Payload>[] payload() default { };

}

