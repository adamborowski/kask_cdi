package pl.gda.pg.eti.kask.javaee.enterprise.view;

import pl.gda.pg.eti.kask.javaee.enterprise.BookService;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Author;

/**
 *
 * @author psysiu
 */
@ViewScoped
@ManagedBean
public class ListAuthors implements Serializable {

    @EJB
    BookService bookService;

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
    private List<Author> authors;

    public List<Author> getAuthors() {
        if (authors == null) {
            authors = bookService.findAllAuthors();
        }
        return authors;
    }
}
