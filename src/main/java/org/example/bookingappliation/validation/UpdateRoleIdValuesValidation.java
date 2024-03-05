package org.example.bookingappliation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.bookingappliation.model.user.RoleType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateRoleIdValuesValidation implements ConstraintValidator<UpdateRoleIdValues, Long> {
    private static final int ROLE_TYPE_LENGTH = RoleType.RoleName.getRolesInOrder().size();

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        return ((id != null) && (id > 0) && (id <= ROLE_TYPE_LENGTH));
    }
}
