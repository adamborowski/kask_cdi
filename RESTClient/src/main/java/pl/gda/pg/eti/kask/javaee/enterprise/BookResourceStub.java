package pl.gda.pg.eti.kask.javaee.enterprise;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Library;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Sorcerer;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Tower;

/**
 *
 * @author psysiu
 */
public class BookResourceStub {

    private final Client client;

    private final WebTarget root;

    public BookResourceStub(String baseAddress, String login, String password, SslConfigurator sslConfig) {
        SSLContext sslContext = sslConfig.createSSLContext();
        client = ClientBuilder.newBuilder().sslContext(sslContext).build();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(login, password);
        client.register(feature);
        root = client.target(baseAddress + "/resources/towers");
    }

    public Library findTowers() {
        return root.request(MediaType.APPLICATION_JSON_TYPE).get(Library.class);
    }

    public int saveNewTower(Tower tower) {
        return root.request().post(Entity.entity(tower, MediaType.APPLICATION_JSON_TYPE)).getStatus();
    }

    public int saveNewWizzard(int towerId, Sorcerer wizzard) {
        return root.path("/tower/{towerId: [0-9]+}/wizzards/new")
                .resolveTemplate("towerId", towerId)
                .request()
                .post(Entity.entity(wizzard, MediaType.APPLICATION_JSON_TYPE))
                .getStatus();
    }

    public int saveNewAuthor(String name, String surname) {
        Form form = new Form();
        form.param("name", name);
        form.param("surname", surname);
        return root.path("/authors").request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE)).getStatus();
    }
}
