package bg.nbuteam4.myschool.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PersonalCodeValidator implements ConstraintValidator<PersonalCode, String> {
    private static final int[] WEIGHTS = {
            2, 4, 8, 5, 10, 9, 7, 3, 6
    };

    @Override
    public void initialize(PersonalCode constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext constraintValidatorContext) {
        if (input == null || input.isEmpty()) {
            return true;
        }

        if (input.length() != 10 || !input.matches("\\d+")) {
            return false;
        }

        int checksum = 0;
        for (int i = 0; i < 9; i++) {
            checksum += Character.getNumericValue(input.charAt(i)) * WEIGHTS[i];
        }

        checksum %= 11;

        if (checksum == 10) {
            checksum = 0;
        }

        return checksum == Character.getNumericValue(input.charAt(9));
    }
}
