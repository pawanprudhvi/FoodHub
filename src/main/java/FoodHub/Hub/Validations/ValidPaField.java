package FoodHub.Hub.Validations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = paconfig.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
    public @interface ValidPaField {
        String message() default "Field must be at least 6 characters long and start with 'pa'";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default{};
    }
