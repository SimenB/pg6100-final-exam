package no.nith.pg6100.infrastructure;

import no.nith.pg6100.entity.Author;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class AuthorDaoTest {
    @Rule
    public H2Setup h2Setup = new H2Setup();
    private AuthorDao authorDao;

    @Before
    public void setUp() {
        authorDao = new AuthorDao();
        authorDao.setEntityManager(h2Setup.entityManager());
    }

    @Test
    public void testFindAll() {
        List<Author> authors = authorDao.findAll();
        assertTrue(authors.size() >= 3);
    }

}