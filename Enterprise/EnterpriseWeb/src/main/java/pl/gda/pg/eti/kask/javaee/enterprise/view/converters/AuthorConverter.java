package pl.gda.pg.eti.kask.javaee.enterprise.view.converters;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import pl.gda.pg.eti.kask.javaee.enterprise.BookService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Author;

/**
 *
 * @author psysiu
 */
@ManagedBean
@RequestScoped
public class AuthorConverter implements Converter {

    @EJB
    BookService bookService;

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if ("---".equals(value)) {
            return null;
        }
        return bookService.findAuthor(Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "---";
        }
        return ((Author) value).getId() + "";
    }
}
