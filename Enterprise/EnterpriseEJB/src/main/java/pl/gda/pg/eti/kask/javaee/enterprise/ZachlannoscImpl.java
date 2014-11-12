/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gda.pg.eti.kask.javaee.enterprise;

import java.io.Serializable;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Sorcerer;

/**
 *
 * @author adam
 */
@Interceptor
@Zachlannosc
public class ZachlannoscImpl implements Serializable {

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        //dodatkoweczynnosciwstepne
        System.out.println("KURWA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Sorcerer wiz = (Sorcerer) context.getParameters()[0];
        System.out.println(wiz.getId());
        Object result = context.proceed();
        //dodatkoweczynnoscikonczace
        return result;
    }
}
