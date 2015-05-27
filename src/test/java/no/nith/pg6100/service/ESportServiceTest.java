package no.nith.pg6100.service;

import no.nith.pg6100.entity.Result;
import no.nith.pg6100.infrastructure.ESportServiceWebService;
import no.nith.pg6100.infrastructure.ResultDao;
import no.nith.pg6100.soap.service.Team;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ESportServiceTest {
    @Mock
    private UriInfo uriInfo;
    @Mock
    private ESportServiceWebService eSportServiceWebService;
    @Mock
    private ResultDao resultDao;

    @InjectMocks
    private ESportService objectUnderTest;

    @Before
    public void setUp() throws Exception {
        when(eSportServiceWebService.getGames()).thenReturn(Optional.<List<String>>empty());
        when(eSportServiceWebService.getTeams(anyString())).thenReturn(Optional.<List<Team>>empty());
    }

    @Test
    public void testGetAllGames() throws Exception {
        objectUnderTest.getAllGames();

        verify(eSportServiceWebService).getGames();

        verifyNoMoreInteractions(eSportServiceWebService);
        verifyZeroInteractions(resultDao, uriInfo);
    }

    @Test
    public void testGetAllTeams() throws Exception {
        objectUnderTest.getAllTeams("blabla");

        verify(eSportServiceWebService).getTeams("blabla");

        verifyNoMoreInteractions(eSportServiceWebService);
        verifyZeroInteractions(resultDao, uriInfo);
    }

    @Test
    public void testSaveResult() throws Exception {
        final UriBuilder uriBuilder = mock(UriBuilder.class);
        when(uriInfo.getAbsolutePathBuilder()).thenReturn(uriBuilder);
        when(uriBuilder.path(anyString())).thenReturn(uriBuilder);
        when(resultDao.getTotalNumberOfResults()).thenReturn(5);

        final Result result = new Result();

        objectUnderTest.saveResult(result);

        verify(uriInfo).getAbsolutePathBuilder();
        verify(resultDao).getTotalNumberOfResults();
        verify(uriBuilder).path("/customer/4");
        verify(uriBuilder).build();
        verify(resultDao).persist(result);

        verifyNoMoreInteractions(resultDao, uriInfo, uriBuilder);
        verifyZeroInteractions(eSportServiceWebService);
    }

    @Test
    public void testGetResultsById() throws Exception {
        objectUnderTest.getResultsById(1);

        verify(resultDao).findById(1);

        verifyNoMoreInteractions(resultDao);
        verifyZeroInteractions(uriInfo, eSportServiceWebService);
    }

    @Test
    public void testGetResultsForTeam() throws Exception {
        objectUnderTest.getResultsForTeam(1);

        verify(resultDao).findAllByTeam(1);

        verifyNoMoreInteractions(resultDao);
        verifyZeroInteractions(uriInfo, eSportServiceWebService);
    }
}
