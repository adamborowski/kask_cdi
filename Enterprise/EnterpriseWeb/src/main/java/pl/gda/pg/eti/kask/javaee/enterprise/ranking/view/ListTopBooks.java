package pl.gda.pg.eti.kask.javaee.enterprise.ranking.view;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Delegate;
import pl.gda.pg.eti.kask.javaee.enterprise.ranking.TopBooks;

/**
 *
 * @author psysiu
 */
@Named
@ViewScoped
public class ListTopBooks implements Serializable {

    @Inject
    @Delegate
    TopBooks topBooks;

}
