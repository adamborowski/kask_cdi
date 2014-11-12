package pl.adamborowski.kask.jsf.view;

import lombok.NoArgsConstructor;
import pl.gda.pg.eti.kask.javaee.enterprise.TowerService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Sorcerer;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Tower;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;

/**
 * @author psysiu
 */
@RequestScoped
@ManagedBean
@NoArgsConstructor
public class ListTowers implements Serializable {

    public void setTowerService(TowerService towerService) {
        this.towerService = towerService;
    }

    @EJB
    TowerService towerService;

    private List<Tower> towers;
    private List<Sorcerer> wizzards;

    public List<Tower> getTowers() {
        if (towers == null) {
            towers = towerService.findAllTowers();
        }
        return towers;
    }

    public List<Sorcerer> getWizzards() {
        if (wizzards == null) {
            wizzards = towerService.findAllWizzards();
        }
        return wizzards;
    }

    public void removeTower(Tower tower) {
        towerService.removeTower(tower);
        towers.remove(tower);
    }
    
    public void training(int amt){
        
        towerService.trainWizzards(amt);
        wizzards=getWizzards();
    }


}
