package pl.gda.pg.eti.kask.javaee.enterprise;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import lombok.extern.java.Log;
import org.apache.commons.collections.CollectionUtils;
import pl.gda.pg.eti.kask.javaee.enterprise.cdi.MyManager;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Author;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Library;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.ObjectFactory;

/**
 *
 * @author psysiu
 */
@Stateless
@LocalBean
@Log
public class BookService implements Serializable {

    @Inject @MyManager
    EntityManager em;

    public BookService() {
    }

    public List<Book> findAllBooks() {
        return em.createNamedQuery("Book.findAll").getResultList();
    }

    public Book findBook(int id) {
        return em.find(Book.class, id);
    }

    public void removeBook(Book book) {
        em.remove(em.merge(book));
    }

    public void saveBook(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            for (Author author : book.getAuthors()) {
                Author managedAuthor = findAuthor(author.getId());
                managedAuthor.getBooks().add(book);
            }
        } else {
            Book originalBook = findBook(book.getId());
            em.detach(originalBook);
            book = em.merge(book);
            Collection<Author> removedAuthors = CollectionUtils.subtract(originalBook.getAuthors(), book.getAuthors());
            Collection<Author> addedAuthors = CollectionUtils.subtract(book.getAuthors(), originalBook.getAuthors());
            for (Author author : removedAuthors) {
                Author managedAuthor = findAuthor(author.getId());
                managedAuthor.getBooks().remove(originalBook);
            }
            for (Author author : addedAuthors) {
                author.getBooks().add(originalBook);
            }
        }
    }

    public List<Author> findAllAuthors() {
        return em.createNamedQuery("Author.findAll").getResultList();
    }

    public Author findAuthor(int id) {
        return em.find(Author.class, id);
    }

    public void marshalLibrary(OutputStream out) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class.getPackage().getName());
            Marshaller m = jaxbContext.createMarshaller();
            Library library = new Library();
            library.getBooks().addAll(findAllBooks());
            m.marshal(library, out);
        } catch (JAXBException ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
        }
    }
}
