package pl.gda.pg.eti.kask.javaee.enterprise.ranking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.faces.application.FacesMessage;
import lombok.Getter;
import lombok.extern.java.Log;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;
import pl.gda.pg.eti.kask.javaee.enterprise.TowerService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Sorcerer;

/**
 *
 * @author psysiu
 */
@ApplicationScoped
@Log
public class TopBooks implements Serializable, BookList {

    @Getter
    private List<Sorcerer> books;
    @EJB
    TowerService towerService;

    @PostConstruct
    private void init() {
        books = towerService.getBestWizzards(numWizzards);
    }
    private int numWizzards=3;

    public void processNewBook(@Observes Sorcerer book) {
//        books.add(book);
        books = towerService.getBestWizzards(numWizzards);
//        PushContext pushContext = PushContextFactory.getDefault().getPushContext();
//        pushContext.push("/notifications", new FacesMessage("", ""));
        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish("/notifications", new FacesMessage("", ""));
    }
}
