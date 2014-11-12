package pl.gda.pg.eti.kask.javaee.enterprise.entities.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author psysiu
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InPastValidator.class)
public @interface InPast {
    
    String message() default "{pl.gda.pg.eti.kask.javaee.enterprise.entities.validators.InPast.IN_PAST}";
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    String date() default "";
    
}
