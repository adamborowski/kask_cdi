package pl.gda.pg.eti.kask.javaee.enterprise.view;

import java.io.IOException;
import java.io.OutputStream;
import pl.gda.pg.eti.kask.javaee.enterprise.BookService;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;

/**
 *
 * @author psysiu
 */
@ViewScoped
@ManagedBean
public class ListBooks implements Serializable {

    @EJB
    BookService bookService;

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
    private List<Book> books;

    public List<Book> getBooks() {
        if (books == null) {
            books = bookService.findAllBooks();
        }
        return books;
    }

    public void removeBook(Book book) {
        bookService.removeBook(book);
        books.remove(book);
    }

    public void downloadLibraryXML() throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
        ec.setResponseContentType("text/xml"); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"library.xml\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

        OutputStream output = ec.getResponseOutputStream();

        bookService.marshalLibrary(output);

        fc.responseComplete(); // Important! Otherwise JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.
    }
}
