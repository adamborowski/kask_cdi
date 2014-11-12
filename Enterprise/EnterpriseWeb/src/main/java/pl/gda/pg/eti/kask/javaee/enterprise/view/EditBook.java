package pl.gda.pg.eti.kask.javaee.enterprise.view;

import pl.gda.pg.eti.kask.javaee.enterprise.BookService;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Author;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Comics;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Cover;

/**
 *
 * @author psysiu
 */
@ViewScoped
@ManagedBean
@Log
public class EditBook implements Serializable {

    @EJB
    BookService bookService;

    @Getter
    @Setter
    private int bookId;

    @Getter
    @Setter
    private Author a;

    @Getter
    @Setter
    private Book book;

    @Setter
    private boolean comics;

    private List<SelectItem> authorsAsSelectItems;

    private List<SelectItem> coversAsSelectItems;

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public List<SelectItem> getAuthorsAsSelectItems() {
        if (authorsAsSelectItems == null) {
            authorsAsSelectItems = new ArrayList<>();
            for (Author author : bookService.findAllAuthors()) {
                authorsAsSelectItems.add(new SelectItem(author, author.getName() + " " + author.getSurname()));
            }
        }
        return authorsAsSelectItems;
    }

    public List<SelectItem> getCoversAsSelectItems() {
        if (coversAsSelectItems == null) {
            coversAsSelectItems = new ArrayList<>();
            coversAsSelectItems.add(new SelectItem(null, "---"));
            for (Cover cover : Cover.values()) {
                coversAsSelectItems.add(new SelectItem(cover, cover.name().toLowerCase()));
            }
        }
        return coversAsSelectItems;
    }

    public void init() {
        if (book == null && bookId != 0) {
            book = bookService.findBook(bookId);
        } else if (book == null && bookId == 0) {
            if (comics) {
                book = new Comics();
            } else {
                book = new Book();
            }
        }
        if (book == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("error/404.xhtml");
            } catch (IOException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }
    }

    public String saveBook() {
        try {
            bookService.saveBook(book);
            return "list_books?faces-redirect=true";
        } catch (Exception ex) {
            ResourceBundle bundle = ResourceBundle.getBundle("pl.gda.pg.eti.kask.javaee.enterprise.view.messages");
            FacesContext.getCurrentInstance().addMessage("form", new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("bookSaveError"), bundle.getString("bookSaveError")));
            return null;
        }
    }
    
    public boolean isComics() {
        return book instanceof Comics;
    }
}
