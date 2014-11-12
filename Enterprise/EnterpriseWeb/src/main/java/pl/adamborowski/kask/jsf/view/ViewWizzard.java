package pl.adamborowski.kask.jsf.view;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.enterprise.TowerService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Sorcerer;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Tower;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import javax.ejb.EJB;

/**
 * @author psysiu
 */
@ViewScoped
@ManagedBean
@Log
public class ViewWizzard implements Serializable {

    @EJB
     TowerService towerService;

    @Getter
    @Setter
    private int wizzardId;

    @Getter
    @Setter
    private Sorcerer wizzard;

    public void setTowerService(TowerService towerService) {
        this.towerService = towerService;
    }

    public void init() {
        if (wizzard == null) {
            wizzard = towerService.findWizzard(wizzardId);
        }
        if (wizzard == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("error/404.xhtml");
            } catch (IOException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }
    }
}
