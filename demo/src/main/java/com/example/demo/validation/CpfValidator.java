package com.example.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<CpfValido, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!value.matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$"))
            return false;

        value = value.replace(".", "").replace("-", "");

        var equals = 0;
        for (var i = 0; i < value.length() - 1; i++) {
            if (value.charAt(i) == value.charAt(i + 1)) {
                equals = equals + 1;
            }
        }
        if (equals == 10)
            return false;

        var j = 10;
        var sumFirstDigit = 0;
        var sumSecondDigit = 0;

        for (var i = 0; i < 9; i++) {
            sumFirstDigit = sumFirstDigit + Character.getNumericValue(value.charAt(i)) * j;
            j--;
        }

        j = 11;
        for (var i = 0; i < 10; i++) {
            sumSecondDigit = sumSecondDigit + Character.getNumericValue(value.charAt(i)) * j;
            j--;
        }

        var remainderFirstDigit = sumFirstDigit * 10 % 11;
        if (remainderFirstDigit == 10) {
            remainderFirstDigit = 0;
        }

        var remainderSecondDigit = sumSecondDigit * 10 % 11;

        return remainderFirstDigit == Character.getNumericValue(value.charAt(9))
                && remainderSecondDigit == Character.getNumericValue(value.charAt(10));
    }
}
