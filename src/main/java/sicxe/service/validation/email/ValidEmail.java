package sicxe.service.validation.email;



import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by maciek on 27/01/16.
 */

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE, })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {
    String message() default "Invalid email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

