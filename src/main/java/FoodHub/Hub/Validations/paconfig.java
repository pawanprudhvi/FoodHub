package FoodHub.Hub.Validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class paconfig implements ConstraintValidator<ValidPaField,String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context)
    {
        return s!=null&&s.length()>=6&&s.startsWith("pa");
    }
}
