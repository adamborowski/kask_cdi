/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gda.pg.eti.kask.javaee.enterprise;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.servlet.http.HttpSession;
import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.enterprise.cdi.MyManager;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.User;

/**
 *
 * @author psysiu
 */
@Stateless
@LocalBean
@Log
public class UserService {

    @Inject @MyManager
    EntityManager em;

    public User findUser(String login) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        return em.createQuery(query.where(query.from(User.class).get("login").in(login))).getSingleResult();
    }

    public User findUser(int id) {
        return em.find(User.class, id);
    }

    @RolesAllowed({"Admin", "User"})
    public void saveUser(User user) {
        //todo sprawdź, czy user 
        if (canAccess(user)) {
            if (user.getId() == null) {
                em.persist(user);
            } else {
                if (user.getPassword()==null) {
                    user.setPassword(findUser(user.getId()).getPassword());//nie zmieniaj hasła gdy jest puste pole
                }
                em.merge(user);
            }
        }
    }

    @RolesAllowed({"Admin", "User"})
    public List<User> findAllUsers() {
        if (isAdminMode()) {
            return em.createNamedQuery("User.findAll").getResultList();

        }
        List<User> list = new ArrayList<>();
        list.add(getCurrentUser());
        return list;
    }
    @Resource
    SessionContext sctx;

    public User getCurrentUser() {
        Principal a = sctx.getCallerPrincipal();
        if (a.getName().equals("ANONYMOUS")) {
            return null;
        }
        return findUser(a.getName());
    }

    public boolean isAdmin(User user) {
        return user.getGroup().equals("admin");
    }

    public boolean isAdminMode() {
        return isAdmin(getCurrentUser());
    }

    public boolean canAccess(User user) {
        return isAdminMode() || user.equals(getCurrentUser());
    }

    public void logout() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.invalidate();
    }

}
