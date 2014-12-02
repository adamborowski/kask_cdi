package pl.gda.pg.eti.kask.javaee.enterprise;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;
import org.glassfish.jersey.SslConfigurator;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Author;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException {
        SslConfigurator sslConfig = SslConfigurator.newInstance()
                .trustStoreFile("./cacerts.jks")
                .trustStorePassword("changeit")
                .keyStoreFile("./keystore.jks")
                .keyPassword("changeit");

        BookResourceStub stub = new BookResourceStub("https://llolth:8181/RESTServices", "psysiu", "psysiu", sslConfig);

        List<Book> books = stub.findBooks(0, 2).getBooks();
        
        for (Book book : books) {
            System.out.println(book);
        }
        
        List<Author> authors = stub.findAuthors().getAuthors();
        
        Book book = new Book();
        book.setTitle("Całkiem nowa ksiązka z klienta");
        book.setPublishDate(DateUtils.setYears(new Date(), 2010));
        book.getAuthors().add(authors.get(0));
        System.out.println(stub.saveNewBook(book));
        
        System.out.println(stub.saveNewAuthor("Michał", "Wójcik"));
    }
}
