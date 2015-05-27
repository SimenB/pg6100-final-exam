package no.nith.pg6100.infrastructure;

import no.nith.pg6100.Config;
import no.nith.pg6100.soap.service.EsportService;
import no.nith.pg6100.soap.service.GameResponse;
import no.nith.pg6100.soap.service.Team;
import no.nith.pg6100.soap.service.TeamResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ESportServiceWebServiceTest {
    @Mock
    private Config config;
    @Mock
    private Logger logger;
    @Mock
    private EsportService esportService;
    @Mock
    private GameResponse gameResponse;
    @Mock
    private TeamResponse teamResponse;

    @InjectMocks
    private ESportServiceWebServiceImpl eSportServiceWebService;

    @Before
    public void setUp() throws Exception {
        when(config.getCallerId()).thenReturn("123");
        when(esportService.getGames(anyString())).thenReturn(gameResponse);
        when(esportService.getTeams(anyString(), anyString())).thenReturn(teamResponse);
        when(gameResponse.getGames()).thenReturn(mock(GameResponse.Games.class));
        when(teamResponse.getTeams()).thenReturn(mock(TeamResponse.Teams.class));

        eSportServiceWebService.init();

        verify(config).getCallerId();
        verifyNoMoreInteractions(config);
    }

    @Test
    public void testGetGames() throws Exception {
        eSportServiceWebService.getGames();

        verify(esportService).getGames("123");
        verify(gameResponse).getGames();

        verifyNoMoreInteractions(esportService, gameResponse);

        verifyZeroInteractions(teamResponse, logger);
    }

    @Test
    public void testGetTeams() throws Exception {
        eSportServiceWebService.getTeams("halla");

        verify(esportService).getTeams("123", "halla");
        verify(teamResponse).getTeams();

        verifyNoMoreInteractions(esportService, teamResponse);

        verifyZeroInteractions(gameResponse, logger);
    }

    @Test
    public void testGetGamesException() throws Exception {
        final RuntimeException exception = new RuntimeException();
        when(esportService.getGames(anyString())).thenThrow(exception);

        final Optional<List<String>> result = eSportServiceWebService.getGames();

        assertThat(result, is(equalTo(Optional.empty())));

        verify(esportService).getGames("123");
        verify(logger).error("Error caught", exception);

        verifyNoMoreInteractions(esportService, logger);

        verifyZeroInteractions(teamResponse, gameResponse);
    }

    @Test
    public void testGetTeamsException() throws Exception {
        final RuntimeException exception = new RuntimeException();
        when(esportService.getTeams(anyString(), anyString())).thenThrow(exception);

        final Optional<List<Team>> result = eSportServiceWebService.getTeams("halla");

        assertThat(result, is(equalTo(Optional.empty())));

        verify(esportService).getTeams("123", "halla");
        verify(logger).error("Error caught", exception);

        verifyNoMoreInteractions(esportService, logger);

        verifyZeroInteractions(gameResponse, teamResponse);
    }
}
