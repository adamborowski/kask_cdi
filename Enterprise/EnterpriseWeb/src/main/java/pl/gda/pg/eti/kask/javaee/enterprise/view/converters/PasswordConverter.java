package pl.gda.pg.eti.kask.javaee.enterprise.view.converters;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Mikolaj
 */
@ManagedBean
@RequestScoped
public class PasswordConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        String hashPassword = "";
        if (value.isEmpty()) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(value.getBytes());
            hashPassword = DatatypeConverter.printHexBinary(md.digest());
        } catch (NoSuchAlgorithmException exception) {

        }
        return hashPassword;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return "*******";
    }
}
