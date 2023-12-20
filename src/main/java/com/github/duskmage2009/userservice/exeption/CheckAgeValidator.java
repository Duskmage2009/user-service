package com.github.duskmage2009.userservice.exeption;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class CheckAgeValidator implements ConstraintValidator<CheckAge, LocalDate> {

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate currentDate = LocalDate.now();
        if (currentDate.getYear() - 18 > localDate.getYear()) {
            return true;
        }
        if (currentDate.getYear() - 18 == localDate.getYear()) {
            if (currentDate.getMonth().compareTo(localDate.getMonth()) > 0) {
                return true;
            } else if (currentDate.getMonth().compareTo(localDate.getMonth()) < 0) {
                return false;
            }
            return currentDate.getDayOfMonth() >= localDate.getDayOfMonth();
        }

        return false;
    }
}
