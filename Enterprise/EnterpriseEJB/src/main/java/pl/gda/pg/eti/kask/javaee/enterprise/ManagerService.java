/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gda.pg.eti.kask.javaee.enterprise;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import lombok.Getter;
import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.enterprise.cdi.MyManager;

/**
 *
 * @author 137252ba
 */
@Stateless
@LocalBean
@Log
public class ManagerService implements ManagerServiceLocal {

    @Inject
    @MyManager
    @Getter
    EntityManager em;

}
