package pl.gda.pg.eti.kask.javaee.enterprise.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import pl.gda.pg.eti.kask.javaee.enterprise.UserService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.User;

/**
 *
 * @author psysiu
 */
@ViewScoped
@ManagedBean
public class ListUsers implements Serializable {

    @EJB
    UserService userService;

    private List<User> users;

    public List<User> getUsers() {
        if (users == null) {
            users = new ArrayList<>(userService.findAllUsers());
        }
        return users;
    }

    private User currentUser;

    public User getCurrentUser() {
        if (currentUser == null) {
            currentUser = userService.getCurrentUser();
        }
        return currentUser;
    }

    public boolean isAdminMode() {
        return userService.isAdminMode();
    }

    public void logout() {
        userService.logout();
        currentUser = null;
    }
}
