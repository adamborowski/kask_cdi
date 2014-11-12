package pl.gda.pg.eti.kask.javaee.enterprise.entities;

import javax.xml.bind.annotation.XmlRegistry;

/**
 *
 * @author psysiu
 */
@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public Author createAuthor() {
        return new Author();
    }

    public Library createLibrary() {
        return new Library();
    }

    public Book createBook() {
        return new Book();
    }
    
    public Comics createComics() {
        return new Comics();
    }
}
