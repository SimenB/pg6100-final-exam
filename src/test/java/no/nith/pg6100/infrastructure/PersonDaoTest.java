package no.nith.pg6100.infrastructure;

import no.nith.pg6100.entity.Person;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class PersonDaoTest {
    @Rule
    public H2Setup h2Setup = new H2Setup();
    private PersonDao personDao;

    @Before
    public void setUp() throws Exception {
        personDao = new PersonDao();
        personDao.setEntityManager(h2Setup.entityManager());
    }

    @Test
    public void testFindAll() throws Exception {
        List<Person> people = personDao.findAll();
        assertTrue(people.size() > 0);
    }
}