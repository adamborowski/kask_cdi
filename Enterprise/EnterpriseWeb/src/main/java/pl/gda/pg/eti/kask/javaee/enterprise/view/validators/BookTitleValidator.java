package pl.gda.pg.eti.kask.javaee.enterprise.view.validators;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author psysiu
 */
@ManagedBean
@RequestScoped
public class BookTitleValidator implements Validator {

    private static final String PATTERN = "^[A-Z][a-z0-9]*([ ][A-Za-z0-9]*)*$";

    private static final String WRONG_TITLE = "pl.gda.pg.eti.kask.javaee.enterprise.validators.titleValidator.WRONG_TITLE";

    private static final String WRONG_VALUE = "pl.gda.pg.eti.kask.javaee.enterprise.validators.titleValidator.WRONG_VALUE";

    public ResourceBundle getMessageBundle() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String messageBundleName = facesContext.getApplication().getMessageBundle();
        Locale locale = facesContext.getViewRoot().getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle(messageBundleName, locale);
        return bundle;
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value instanceof String) {
            String title = (String) value;
            if (!Pattern.matches(PATTERN, title)) {
                throw new ValidatorException(new FacesMessage(getMessageBundle().getString(WRONG_TITLE)));
            }
        } else {
            throw new ValidatorException(new FacesMessage(getMessageBundle().getString(WRONG_VALUE)));
        }
    }
}
