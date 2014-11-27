package pl.gda.pg.eti.kask.javaee.enterprise.authorization;

import java.io.Serializable;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.naming.AuthenticationException;
import javax.ws.rs.core.SecurityContext;
import lombok.extern.java.Log;

/**
 *
 * @author psysiu
 */
@Interceptor
@AuthorizeRole
@Log
public class RoleAuthorizer implements Serializable {

    
    @Inject @pl.gda.pg.eti.kask.javaee.enterprise.authorization.SecurityContext
    SecurityContext sc;
    
    

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        String[] roles = context.getMethod().getAnnotation(AuthorizeRole.class).roles();
        boolean authorized = false;
        if (roles.length == 0) {
            authorized = true;
        }
        for (String role : roles) {
            if (sc.isUserInRole(role)) {
                authorized = true;
                break;
            }
        }
        if (!authorized) {
            throw new AuthenticationException();
        }
        Object result = context.proceed();
        return result;
    }
}
