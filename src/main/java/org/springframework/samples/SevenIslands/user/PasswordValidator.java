package org.springframework.samples.SevenIslands.user;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {
    @Override 
    public void initialize(PasswordConstraint password) { 
    }
    @Override 
    public boolean isValid(String contactField, ConstraintValidatorContext cxt) { 
    return contactField != "" && contactField.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&(){}:;<>,.?~_+-=|]).{9,}$"); 
    }
    }