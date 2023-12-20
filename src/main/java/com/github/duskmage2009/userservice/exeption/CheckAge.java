package com.github.duskmage2009.userservice.exeption;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckAgeValidator.class)
public @interface CheckAge {

    public String message() default "Age must be more than [18] years old";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}