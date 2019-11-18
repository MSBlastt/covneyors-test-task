package ru.conveyor.api.validation;

import ru.conveyor.util.PrimeNumberUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PrimeNumberValidator implements ConstraintValidator<PrimeNumberConstraint, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext cxt) {
        return value != null && value > 0 && PrimeNumberUtils.isPrime(value);
    }

}
