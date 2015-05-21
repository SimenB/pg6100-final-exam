package no.nith.pg6100.service;

import no.nith.pg6100.entity.Book;
import org.jboss.resteasy.util.HttpResponseCodes;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static no.nith.pg6100.Constants.BASE_URL;
import static org.junit.Assert.assertEquals;

public class BookServiceIT {

    @Test
    public void getBooks() {
        Response response = ClientBuilder.newClient().target(BASE_URL + "/books").request().get();
        assertEquals(HttpResponseCodes.SC_OK, response.getStatus());

        List<Book> books = response.readEntity(new GenericType<List<Book>>() {
        });
        assertTrue(books.size() >= 3);
    }

    @Test
    public void getBooksInGenre() {
        Response response = ClientBuilder
            .newClient()
            .target(BASE_URL + "/books/genre/Fantasy")
            .request()
            .get();
        assertEquals(HttpResponseCodes.SC_OK, response.getStatus());

        List<Book> books = response.readEntity(new GenericType<List<Book>>() {
        });
        assertTrue(books.size() >= 2);
    }

    @Test
    public void getBooksByAuthor() {
        Response response = ClientBuilder
            .newClient()
            .target(BASE_URL + "/books/author/Terry Pratchett")
            .request()
            .get();
        assertEquals(HttpResponseCodes.SC_OK, response.getStatus());

        List<Book> books = response.readEntity(new GenericType<List<Book>>() {
        });
        assertTrue(books.size() > 0);
    }

    @Test
    public void getBookById() {
        Response response = ClientBuilder
            .newClient()
            .target(BASE_URL + "/books/id/1")
            .request()
            .get();
        assertEquals(HttpResponseCodes.SC_OK, response.getStatus());

        Book book = response.readEntity(Book.class);
        assertEquals("The Colour of Magic", book.getTitle());
    }

    @Test
    public void persistBook() {
        Book book = new Book();
        book.setAuthor("Hamsun");
        book.setTitle("Sult");
        book.setIsbn("12345");

        Response response = ClientBuilder
            .newClient()
            .target(BASE_URL + "/books")
            .request()
            .post(Entity.entity(book, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(HttpResponseCodes.SC_CREATED, response.getStatus());
    }

    @Test
    public void deleteBook() {
        Response response = ClientBuilder.newClient().target(BASE_URL + "/books/id/1").request().delete();
        assertEquals(HttpResponseCodes.SC_NO_CONTENT, response.getStatus());
    }
}
