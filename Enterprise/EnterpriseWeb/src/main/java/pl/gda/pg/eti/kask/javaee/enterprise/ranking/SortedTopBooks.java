package pl.gda.pg.eti.kask.javaee.enterprise.ranking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Sorcerer;

/**
 *
 * @author psysiu
 */
@Decorator
public class SortedTopBooks implements BookList {
    
    @Inject @Delegate @Any
    TopBooks topBooks;
    
    @Override
    public List<Sorcerer> getBooks() {
        ArrayList<Sorcerer> list = new ArrayList<>(topBooks.getBooks());
        Collections.shuffle(list);
        return list;
    }
}
