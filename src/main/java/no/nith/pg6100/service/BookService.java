package no.nith.pg6100.service;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import no.nith.pg6100.entity.Book;
import no.nith.pg6100.infrastructure.BookDao;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/books")
@Api(value = "/books", description = "operations on books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookService {

    @Context
    private UriInfo uriInfo;

    @Inject
    private BookDao bookDao;

    @GET
    @ApiOperation(value = "Finds all books",
        response = Book.class,
        responseContainer = "List")
    public Response getAllBooks() {
        return Response.ok(bookDao.findAll()).build();
    }

    @GET
    @Path("/genre/{genre}")
    public Response getAllBooksInGenre(@PathParam("genre") String genre) {
        return Response.ok(bookDao.findAllInGenre(genre)).build();
    }

    @GET
    @Path("/author/{author}")
    public Response getAllBooksByAuthor(@PathParam("author") String author) {
        return Response.ok(bookDao.findAllByAuthor(author)).build();
    }

    @GET
    @Path("/id/{id}")
    public Response getBook(@PathParam("id") int id) {
        return Response.ok(bookDao.findById(id)).build();
    }

    @POST
    public Response persistBook(Book book) {
        bookDao.persist(book);

        URI uri = uriInfo.getAbsolutePathBuilder().path("id/" + book.getId()).build();
        return Response.created(uri).build();
    }

    @DELETE
    @Path("/id/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        Book book = bookDao.findById(id);
        bookDao.remove(book);
        return Response.noContent().build();
    }
}
