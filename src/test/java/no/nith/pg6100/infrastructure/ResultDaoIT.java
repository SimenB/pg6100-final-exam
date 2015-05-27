package no.nith.pg6100.infrastructure;

import no.nith.pg6100.entity.Result;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class ResultDaoIT {
    @Rule
    public H2Setup h2Setup = new H2Setup();
    private ResultDao resultDao;

    @Before
    public void setUp() {
        resultDao = new ResultDao();
        Whitebox.setInternalState(resultDao, "entityManager", h2Setup.entityManager());
    }

    @Test
    public void testFindAll() throws Exception {
        assertThat(resultDao.findAll(), hasSize(10));
    }

    @Test
    public void testFindAllByTeam() throws Exception {
        assertThat(resultDao.findAllByTeam(2), hasSize(3));
    }

    @Test
    public void testFindById() throws Exception {
        final Result result = resultDao.findById(1);

        assertThat(result.getWinner(), is(2));
        assertThat(result.getLoser(), is(1));
    }

    @Test
    public void testPersist() throws Exception {
        final Result result = new Result();

        result.setLoser(2);
        result.setWinner(3);

        h2Setup.entityManager().getTransaction().begin();
        resultDao.persist(result);
        h2Setup.entityManager().getTransaction().commit();

        assertThat(result.getId(), is(100));
    }

    @Test
    public void testRemove() throws Exception {
        Result book = resultDao.findById(1);
        resultDao.remove(book);

        book = resultDao.findById(1);
        assertThat(book, is(nullValue()));
    }
}
