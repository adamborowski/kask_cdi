package pl.adamborowski.kask.jsf.view.converters;

import javax.ejb.EJB;
import pl.gda.pg.eti.kask.javaee.enterprise.TowerService;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Tower;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * @author psysiu
 */
@ManagedBean
@RequestScoped
public class TowerConverter implements Converter {

    @EJB
    TowerService towerService;

    public void setTowerService(TowerService towerService) {
        this.towerService = towerService;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if ("---".equals(value)) {
            return null;
        }
        return towerService.findTower(Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "---";
        }
        return ((Tower) value).getId() + "";
    }
}
