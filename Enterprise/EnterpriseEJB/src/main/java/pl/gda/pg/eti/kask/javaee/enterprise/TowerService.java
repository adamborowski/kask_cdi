package pl.gda.pg.eti.kask.javaee.enterprise;

import java.util.ArrayList;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Sorcerer;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Tower;
import lombok.extern.java.Log;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import pl.gda.pg.eti.kask.javaee.enterprise.cdi.MyManager;

/**
 * Created by adam on 15.10.14.
 */
@Stateless
@LocalBean
@Log
public class TowerService {

    @EJB
    private UserService userService;
    @Resource
    SessionContext sctx;

    @Inject @MyManager
    EntityManager em;

    @RolesAllowed({"Admin", "User"})
    public List<Tower> findAllTowers() {
        if (userService.isAdminMode()) {
            return em.createNamedQuery("Tower.findAll").getResultList();
        }
        return userService.getCurrentUser().getTowers();
    }

    @RolesAllowed({"Admin", "User"})
    public List<Sorcerer> findAllWizzards() {
        if (userService.isAdminMode()) {
            return em.createNamedQuery("Sorcerer.findAll").getResultList();
        }
        final List<Tower> towers = userService.getCurrentUser().getTowers();
        List<Sorcerer> wizzards = new ArrayList<>();
        for (Tower tower : towers) {
            wizzards.addAll(tower.getWizzards());
        }
        return wizzards;
    }

    @RolesAllowed({"Admin", "User"})
    public Tower findTower(int id) {
        final Tower tower = em.find(Tower.class, id);
        if (userService.canAccess(tower.getUser())) {
            return tower;
        }
        return null;
    }

    @RolesAllowed({"Admin", "User"})
    public Sorcerer findWizzard(int id) {
        final Sorcerer wizzard = em.find(Sorcerer.class, id);
        if (userService.canAccess(wizzard.getTower().getUser())) {
            return wizzard;
        }
        return null;//nie wolno!
    }

    @RolesAllowed({"Admin", "User"})
    public void removeTower(Tower tower) {
        if (userService.canAccess(tower.getUser())) {
            em.remove(em.merge(tower));
        }
    }

    @RolesAllowed({"Admin", "User"})
    public void removeWizzard(Sorcerer wizzard) {
        if (userService.canAccess(wizzard.getTower().getUser())) {
            em.remove(em.merge(wizzard));
        }
    }

    @RolesAllowed({"Admin"})
    public void trainWizzards(int amount) {
        if (userService.isAdminMode()) {
            em.createNamedQuery("Sorcerer.training").setParameter("amount", amount).executeUpdate();
        }
    }

    @RolesAllowed({"Admin", "User"})
    public void saveTower(Tower tower) {
        System.out.println("IDDDD: "+tower.getId());
        if (tower.getId() == null) {
            em.persist(tower);
            tower.setUser(userService.getCurrentUser());
        } else if (userService.canAccess(tower.getUser())) {
            if (tower.getUser() == null) {
                tower.setUser(userService.getCurrentUser());
            }
            em.merge(tower);
        }
    }

    @RolesAllowed({"Admin", "User"})
    public void saveWizzard(Sorcerer wizzard) {
        if (userService.canAccess(wizzard.getTower().getUser())) {
            if (wizzard.getId() == null) {
                em.persist(wizzard);
            } else {
                em.merge(wizzard);
            }
        }
    }

}
