package no.nith.pg6100.infrastructure;

import no.nith.pg6100.entity.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResultDaoTest {
    @Mock
    private Result result;
    @Mock
    private EntityManager entityManager;
    @Mock
    private TypedQuery<Result> typedQuery;

    @InjectMocks
    private ResultDao resultDao;

    @Before
    public void setUp() throws Exception {
        when(entityManager.createNamedQuery(anyString(), eq(Result.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), anyObject())).thenReturn(typedQuery);
    }

    @Test
    public void testFindAll() throws Exception {
        resultDao.findAll();

        verify(entityManager).createNamedQuery("Result.findAll", Result.class);
        verify(typedQuery).getResultList();

        verifyNoMoreInteractions(entityManager, typedQuery);
        verifyZeroInteractions(result);
    }

    @Test
    public void testFindById() throws Exception {
        resultDao.findById(0);

        verify(entityManager).find(Result.class, 0);

        verifyNoMoreInteractions(entityManager);
        verifyZeroInteractions(typedQuery, result);
    }

    @Test
    public void testFindAllByTeam() throws Exception {
        resultDao.findAllByTeam(0);

        verify(entityManager).createNamedQuery("Result.findAllByTeam", Result.class);
        verify(typedQuery).setParameter("team", 0);
        verify(typedQuery).getResultList();

        verifyNoMoreInteractions(entityManager, typedQuery);
        verifyZeroInteractions(result);
    }

    @Test
    public void testGetTotalNumberOfResults() throws Exception {
        resultDao.getTotalNumberOfResults();

        verify(entityManager).createNamedQuery("Result.findAll", Result.class);
        verify(typedQuery).getResultList();

        verifyNoMoreInteractions(entityManager, typedQuery);
        verifyZeroInteractions(result);
    }

    @Test
    public void testPersist() throws Exception {
        resultDao.persist(result);

        verify(entityManager).persist(result);

        verifyNoMoreInteractions(entityManager);
        verifyZeroInteractions(typedQuery, result);
    }

    @Test
    public void testRemove() throws Exception {
        when(entityManager.contains(result)).thenReturn(true);

        resultDao.remove(result);

        verify(entityManager).contains(result);
        verify(entityManager).remove(result);

        verifyNoMoreInteractions(entityManager);
        verifyZeroInteractions(typedQuery, result);
    }

    @Test
    public void testRemoveNotInContext() throws Exception {
        when(entityManager.merge(result)).thenReturn(result);
        resultDao.remove(result);

        verify(entityManager).contains(result);
        verify(entityManager).merge(result);
        verify(entityManager).remove(result);

        verifyNoMoreInteractions(entityManager);
        verifyZeroInteractions(typedQuery, result);
    }
}
