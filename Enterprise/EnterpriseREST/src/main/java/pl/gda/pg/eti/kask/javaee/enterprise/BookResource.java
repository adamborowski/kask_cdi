package pl.gda.pg.eti.kask.javaee.enterprise;

import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.security.auth.message.AuthException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Variant;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.enterprise.authorization.AuthorizeRole;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Library;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Sorcerer;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Tower;
import pl.gda.pg.eti.kask.javaee.enterprise.error.HandleError;

/**
 *
 * @author psysiu
 */
//@Stateless
//@LocalBean
//@DeclareRoles({"Admin", "User"})
@Path("/towers")
@Log
public class BookResource {

    @EJB
    TowerService towerService;

    @Context
    SecurityContext sc;

    @Context
    ServletContext context;

    @Context
    HttpServletRequest request;

    @Context
    HttpServletResponse response;

    @javax.enterprise.inject.Produces
    @RequestScoped
    @pl.gda.pg.eti.kask.javaee.enterprise.authorization.SecurityContext
    public SecurityContext getSecurityContet() {
        return sc;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @AuthorizeRole(roles = {"Admin", "User"})
    @Path("/")
    public Response findTowers() {

        if (sc.isUserInRole("Admin") || sc.isUserInRole("User")) {
            return Response.ok(new Library(towerService.findAllTowers())).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @AuthorizeRole(roles = {"Admin", "User"})
    @Path("/multikulti")
    public Response findMultiKulti(@Context Request request) {

        List<Variant> vs = Variant
                .mediaTypes(MediaType.APPLICATION_JSON_TYPE)
                .languages(Locale.ENGLISH, Locale.forLanguageTag("pl"), Locale.CHINESE, Locale.GERMAN)
                .add().build();

        if (sc.isUserInRole("Admin") || sc.isUserInRole("User")) {
            final List<Tower> allTowers = towerService.findAllTowers();

            Variant v = request.selectVariant(vs);
            if (null == v) {
                return Response.notAcceptable(vs).build();
            } else {
                String  s= v.getLanguageString()+"_";
                for(Tower t:allTowers){
                    t.setName(s+t.getName());
                    for(Sorcerer w:t.getWizzards())
                    {
                        w.setName(s+w.getName());
                    }
                }
                return Response.ok(new Library(allTowers)).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();

    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @AuthorizeRole(roles = {"Admin", "User"})
    @Path("/{id:[0-9]+}")
    public Response findTowerJson(@PathParam("id") Integer id) {
        if (sc.isUserInRole("Admin") || sc.isUserInRole("User")) {
            Tower tower = null;
            try {
                tower = towerService.findTower(id);
            } catch (EJBException ex) {
                if (ex.getCause() instanceof AuthException) {
                    return Response.status(Response.Status.UNAUTHORIZED).build();
                } else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                }
            }
            if (tower != null) {
                return Response.ok(tower).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML})
    @Path("/{id:[0-9]+}")
    @AuthorizeRole(roles = {"Admin", "User"})
    @HandleError
    public Response findTowerXML(@PathParam("id") Integer id) {
        Tower tower = towerService.findTower(id);
        if (tower != null) {
            return Response.ok(new JAXBElement<Tower>(new QName("http://www.eti.pg.gda.pl/kask/javaee/books", "Tower"), Tower.class, tower)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    @AuthorizeRole(roles = {"Admin", "User"})
    @HandleError
    public Response saveNewTower(Tower tower) {
        tower = towerService.saveTower(tower);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/tower/{towerId: [0-9]+}/wizzards/new")
    @AuthorizeRole(roles = {"Admin", "User"})
    @HandleError
    public Response saveNewWizzard(@PathParam("towerId") int id, Sorcerer wizzard) {
        Tower tower = towerService.findTower(id);
        wizzard.setTower(tower);
        wizzard = towerService.saveWizzard(wizzard);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id:[0-9]+}")
    @AuthorizeRole(roles = {"Admin", "User"})
    @HandleError
    public Response saveTower(Tower tower) {
        Tower oldTower = towerService.findTower(tower.getId());
        if (oldTower == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            towerService.saveTower(tower);
            return Response.status(Response.Status.OK).build();
        }
    }

    @DELETE
    @Path("/{id:[0-9]+}")
    @AuthorizeRole(roles = {"Admin", "User"})
    @HandleError
    public Response deleteTower(@PathParam("id") Integer id) {
        Tower tower = towerService.findTower(id);
        if (tower == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            towerService.removeTower(tower);
            return Response.status(Response.Status.OK).build();
        }
    }

}
