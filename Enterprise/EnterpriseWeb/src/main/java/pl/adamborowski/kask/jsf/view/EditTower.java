package pl.adamborowski.kask.jsf.view;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.enterprise.TowerService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Tower;
/**
 * @author psysiu
 */
@ViewScoped
@ManagedBean(name = "editTower")
@Log
public class EditTower implements Serializable {

    @EJB
    TowerService towerService;

    @Getter
    @Setter
    private int towerId;


    @Getter
    @Setter
    private Tower tower;


    public void setTowerService(TowerService towerService) {
        this.towerService = towerService;
    }

    public void init() {
        if (tower == null && towerId != 0) {
            tower = towerService.findTower(towerId);
        } else if (tower == null && towerId == 0) {
            tower = new Tower();
        }
        if (tower == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("error/404.xhtml");
            } catch (IOException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }
    }

    public String saveTower() {
        towerService.saveTower(tower);
        return "list_books?faces-redirect=true";
    }
}
