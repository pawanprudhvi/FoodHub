package FoodHub.Hub.Validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.apache.catalina.Group;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy=GmailOnly.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Gmail {

    String message() default "Email must be valid";
    Class<?>[] groups() default {};


    Class<? extends Payload>[] payload() default {};
}
