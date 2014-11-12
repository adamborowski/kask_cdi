package pl.adamborowski.kask.jsf.view;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.enterprise.TowerService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Environment;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Sorcerer;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Tower;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author psysiu
 */
@ConversationScoped
@Named
@Log
public class EditWizzard implements Serializable {

    @Inject
    Conversation conv;
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
        if (wizzard == null && wizzardId != 0) {
            wizzard = towerService.findWizzard(wizzardId);
        } else if (wizzard == null && wizzardId == 0) {
            wizzard = new Sorcerer();
        }
        if (wizzard == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("error/404.xhtml");
            } catch (IOException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }
        else{
            if (conv.isTransient()) {
                conv.begin();
            }
        }
        //todo zapisać poprzedni tower??
    }

    private List<SelectItem> environmentsAsSelectItems;

    public List<SelectItem> getEnvironmentsAsSelectItems() {
        if (environmentsAsSelectItems == null) {
            environmentsAsSelectItems = new ArrayList<>();
            for (Environment environment : Environment.values()) {
                environmentsAsSelectItems.add(new SelectItem(environment, environment.toString()));
            }
        }
        return environmentsAsSelectItems;
    }

    private List<SelectItem> towersAsSelectItems;

    public List<SelectItem> getTowersAsSelectItems() {
        if (towersAsSelectItems == null) {
            towersAsSelectItems = new ArrayList<>();
            for (Tower tower : towerService.findAllTowers()) {
                towersAsSelectItems.add(new SelectItem(tower, tower.getName()));
            }
        }
        return towersAsSelectItems;
    }

    public String saveWizzard() {
        towerService.saveWizzard(wizzard);
        conv.end();
        return "view_tower?faces-redirect=true&towerId=" + wizzard.getTower().getId();
    }
    
    public String getCID() {
        return conv.getId();
    }
}
