package com.github.duskmage2009.userservice.exeption;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckDateValidator.class)
public @interface CheckDate {

    public String message() default "From should be less than too";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}

