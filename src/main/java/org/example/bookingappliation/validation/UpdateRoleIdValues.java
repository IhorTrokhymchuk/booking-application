package org.example.bookingappliation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UpdateRoleIdValuesValidation.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UpdateRoleIdValues {
    String message() default "parameters invalid role id";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
