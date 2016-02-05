package sicxe.service.validation.password;

import sicxe.view.user.UserFormDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by maciek on 28/01/16.
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserFormDto user = (UserFormDto) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}