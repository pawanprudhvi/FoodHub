package FoodHub.Hub.Validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GmailOnly implements ConstraintValidator<Gmail,String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
       return email!=null&&email.toLowerCase().endsWith("gmail.com");
    }
}
