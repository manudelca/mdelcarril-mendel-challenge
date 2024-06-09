package com.mendel.challenge.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, CharSequence> {
    private List<String> acceptedValues;
    private String messageTemplate;

    @Override
    public void initialize(ValueOfEnum annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
        messageTemplate = annotation.message();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        boolean isValid = acceptedValues.contains(value.toString().toUpperCase());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            String actualMessage = messageTemplate.replace("{enumValues}", acceptedValues.toString());
            context.buildConstraintViolationWithTemplate(actualMessage)
                    .addConstraintViolation();
        }

        return isValid;
    }
}
