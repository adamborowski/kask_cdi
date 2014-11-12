package pl.gda.pg.eti.kask.javaee.enterprise.entities.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author psysiu
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GoodManaValidator.class)
public @interface GoodMana {

    String message() default "{pl.adamborowski.kask.jsf.entities.validators.goodMana}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int maximumMana() default 1000;

    int dividableBy() default 1;

}
