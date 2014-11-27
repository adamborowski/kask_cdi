package pl.gda.pg.eti.kask.javaee.enterprise;

import java.io.IOException;
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
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Author;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Authors;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Book;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Books;
import pl.gda.pg.eti.kask.javaee.enterprise.entities.Library;
import pl.gda.pg.eti.kask.javaee.enterprise.error.HandleError;

/**
 *
 * @author psysiu
 */
//@Stateless
//@LocalBean
//@DeclareRoles({"Admin", "User"})
@Path("/books")
@Log
public class BookResource {

    @EJB
    BookService bookService;

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
    @Path("/")
//    @RolesAllowed({"Admin", "User"})
    public Response findBooks(@DefaultValue("0") @QueryParam("offset") int offset, @DefaultValue("0") @QueryParam("limit") int limit) {
        if (sc.isUserInRole("Admin") || sc.isUserInRole("User")) {
            if (limit > 0) {
                return Response.ok(new Library(bookService.findAllBooks(offset, limit))).build();
            } else {
                return Response.ok(new Library(bookService.findAllBooks())).build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id:[0-9]+}")
    public Response findBookJson(@PathParam("id") Integer id) {
        if (sc.isUserInRole("Admin") || sc.isUserInRole("User")) {
            Book book = null;
            try {
                book = bookService.findBook(id);
            } catch (EJBException ex) {
                if (ex.getCause() instanceof AuthException) {
                    return Response.status(Response.Status.UNAUTHORIZED).build();
                } else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                }
            }
            if (book != null) {
                return Response.ok(book).build();
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
    public Response findBookXML(@PathParam("id") Integer id) {
        Book book = bookService.findBook(id);
        if (book != null) {
            return Response.ok(new JAXBElement<Book>(new QName("http://www.eti.pg.gda.pl/kask/javaee/books", "Book"), Book.class, book)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    @AuthorizeRole(roles = {"Admin", "User"})
    @HandleError
    public Response saveNewBook(Book book) {
        book = bookService.saveBook(book);
        return Response.status(Response.Status.CREATED).header("Location", "books/" + book.getId()).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id:[0-9]+}")
    @AuthorizeRole(roles = {"Admin", "User"})
    @HandleError
    public Response saveBook(Book book) {
        Book oldBook = bookService.findBook(book.getId());
        if (oldBook == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            bookService.saveBook(book);
            return Response.status(Response.Status.OK).build();
        }
    }

    @DELETE
    @Path("/{id:[0-9]+}")
    @AuthorizeRole(roles = {"Admin", "User"})
    @HandleError
    public Response deleteBook(@PathParam("id") Integer id) {
        Book book = bookService.findBook(id);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            bookService.removeBook(book);
            return Response.status(Response.Status.OK).build();
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id:[0-9]+}/authors")
    @AuthorizeRole(roles = {"Admin", "User"})
    @HandleError
    public Response findBookAuthors(@PathParam("id") Integer id) {
        Book book = bookService.findBook(id);
        if (book != null) {
            return Response.ok(new Authors(book.getAuthors())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{id:[0-9]+}/title")
    @AuthorizeRole(roles = {"Admin", "User"})
    @HandleError
    public Response findBookTitle(@PathParam("id") int id) {
        Book book = bookService.findBook(id);
        if (book != null) {
            return Response.ok(book.getTitle()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/authors")
    @AuthorizeRole(roles = {"Admin", "User"})
    @HandleError
    public Response findAuthors() {
        return Response.ok(new Authors(bookService.findAllAuthors())).build();
    }

    @GET
    @Path("/authors/{id:[0-9]+}/books")
    @AuthorizeRole(roles = {"Admin", "User"})
    @HandleError
    public Response findAuthorBooks(@PathParam("id") Integer id, @Context Request r) {
        Author author = bookService.findAuthor(id);
        List<Variant> vs = Variant
                .mediaTypes(MediaType.APPLICATION_XML_TYPE, MediaType.APPLICATION_JSON_TYPE)
                .languages(Locale.ENGLISH, Locale.forLanguageTag("pl"))
                .add().build();
        if (author != null) {
            Variant v = r.selectVariant(vs);
            if (v == null) {
                return Response.notAcceptable(vs).build();
            } else {
                for (Book book : author.getBooks()) {
                    book.setTitle(book.getTitle() + "-" + v.getLanguageString());
                }
                return Response.ok(new Books(author.getBooks())).build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @POST
    @Path("/authors")
    public Response saveNewAuthor(
            @FormParam("name") String name,
            @FormParam("surname") String surname) {
        Author author = new Author();
        author.setName(name);
        author.setSurname(surname);
        author = bookService.saveAuthor(author);
        return Response.status(Response.Status.CREATED).header("Location", "books/authors/" + author.getId()).build();
    }
}
