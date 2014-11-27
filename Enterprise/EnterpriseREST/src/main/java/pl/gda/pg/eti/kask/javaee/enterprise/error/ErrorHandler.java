package pl.gda.pg.eti.kask.javaee.enterprise.error;

import pl.gda.pg.eti.kask.javaee.enterprise.authorization.*;
import java.io.Serializable;
import javax.ejb.AccessLocalException;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.naming.AuthenticationException;
import javax.ws.rs.core.Response;
import lombok.extern.java.Log;

/**
 *
 * @author psysiu
 */
@Interceptor
@AuthorizeRole
@Log
public class ErrorHandler implements Serializable {

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        Object result = null;
        try {
            result = context.proceed();
        } catch (Exception ex) {
            if (ex instanceof AuthenticationException) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            } else if (ex.getCause() != null && ex.getCause() instanceof AccessLocalException) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return result;
    }
}
