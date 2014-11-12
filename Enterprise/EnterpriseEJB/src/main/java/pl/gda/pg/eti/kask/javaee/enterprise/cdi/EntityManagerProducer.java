/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gda.pg.eti.kask.javaee.enterprise.cdi;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author adam
 */
public class EntityManagerProducer {

    @PersistenceContext
    EntityManager em;

    @Produces
    @MyManager
    public EntityManager getManager() {
        return em;
    }

}
