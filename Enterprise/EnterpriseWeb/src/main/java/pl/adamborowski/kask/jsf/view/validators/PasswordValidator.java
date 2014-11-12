package pl.adamborowski.kask.jsf.view.validators;

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
 * @author psysiu
 */
@ManagedBean
@RequestScoped
public class PasswordValidator implements Validator {

    private static final String PATTERN = "^(?=.*\\d).{4,8}$";


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
            System.out.println("title: "+title);
            if (!Pattern.matches(PATTERN, title)) {
                throw new ValidatorException(new FacesMessage("Password must be between 4 and 8 digits long and include at least one numeric digit."));
            }
        } else {
            throw new ValidatorException(new FacesMessage("Text only"));
        }
    }
}
