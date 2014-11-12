package pl.adamborowski.kask.jsf.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.enterprise.TowerService;
import pl.gda.pg.eti.kask.javaee.enterprise.UserService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Tower;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.User;

/**
 * @author psysiu
 */
@ViewScoped
@ManagedBean(name = "editUser")
@Log
public class EditUser implements Serializable {

    @EJB
    UserService userService;

    @Getter
    @Setter
    private int userId;

    @Getter
    @Setter
    private User user;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void init() {
        if (user == null && userId!=0) {
            user = userService.findUser(userId);
        } else if (user == null && userId==0) {
            user = new User();
        }
        if (user == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("error/404.xhtml");
            } catch (IOException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }
    }

    public String saveUser() {
         try {
            userService.saveUser(user);
            return "users?faces-redirect=true";
        } catch (Exception ex) {
            ResourceBundle bundle = ResourceBundle.getBundle("pl.gda.pg.eti.kask.javaee.enterprise.view.messages");
            FacesContext.getCurrentInstance().addMessage("form", new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("bookSaveError"), bundle.getString("bookSaveError")));
            return null;
        }
    }
}
