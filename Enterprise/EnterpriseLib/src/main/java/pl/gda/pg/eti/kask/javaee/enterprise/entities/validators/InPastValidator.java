package pl.gda.pg.eti.kask.javaee.enterprise.entities.validators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.java.Log;

/**
 *
 * @author psysiu
 */
@Log
public class InPastValidator implements ConstraintValidator<InPast, Date> {

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    private Date date;

    @Override
    public void initialize(InPast constraintAnnotation) {
        if (!constraintAnnotation.date().equals("")) {
            try {
                date = new SimpleDateFormat(DATE_PATTERN).parse(constraintAnnotation.date());
            } catch (ParseException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                date = null;
            }
        }
    }

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        if (date != null) {
            return date.after(value);
        } else {
            return new Date().after(value);
        }
    }
}
