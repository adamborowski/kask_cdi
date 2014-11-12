package pl.gda.pg.eti.kask.javaee.enterprise.entities.validators;

import lombok.extern.java.Log;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author psysiu
 */
@Log
public class GoodManaValidator implements ConstraintValidator<GoodMana, Integer> {


    private Integer maxMana;
    private Integer dividableBy;

    @Override
    public void initialize(GoodMana constraintAnnotation) {
        maxMana = constraintAnnotation.maximumMana();
        dividableBy = constraintAnnotation.dividableBy();
    }


    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value > 0 && value < maxMana && value % dividableBy == 0;
    }
}
