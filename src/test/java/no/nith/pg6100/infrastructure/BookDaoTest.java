package no.nith.pg6100.infrastructure;

import no.nith.pg6100.entity.Book;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class BookDaoTest {

    @Rule
    public H2Setup h2Setup = new H2Setup();
    private BookDao bookDao;

    @Before
    public void setUp() {
        bookDao = new BookDao();
        bookDao.setEntityManager(h2Setup.entityManager());
    }

    @Test
    public void findAll() {
        List<Book> books = bookDao.findAll();
        assertTrue(books.size() >= 12);
    }

    @Test
    public void findAllInGenre() {
        List<Book> books = bookDao.findAllInGenre("Fantasy");
        assertTrue(books.size() >= 2);
    }

    @Test
    public void findAllByAuthor() {
        List<Book> books = bookDao.findAllByAuthor("Terry Pratchett");
        assertTrue(books.size() > 0);
    }

    @Test
    public void findById() {
        Book book = bookDao.findById(1);
        assertEquals("The Colour of Magic", book.getTitle());
    }

    @Test
    public void persist() {
        Book book = new Book();
        book.setTitle("The Holy Bi$$le");
        book.setAuthor("Jezus");
        book.setGenre("Reality");
        book.setIsbn("420-420");

        h2Setup.entityManager().getTransaction().begin();
        bookDao.persist(book);
        h2Setup.entityManager().getTransaction().commit();

        assertTrue(book.getId() > 0);
    }

    @Test
    public void remove() {
        Book book = bookDao.findById(1);
        bookDao.remove(book);

        book = bookDao.findById(1);
        assertNull(book);
    }
}