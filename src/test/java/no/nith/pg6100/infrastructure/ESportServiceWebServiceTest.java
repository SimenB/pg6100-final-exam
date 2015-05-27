package no.nith.pg6100.infrastructure;

import no.nith.pg6100.Config;
import no.nith.pg6100.soap.service.EsportService;
import no.nith.pg6100.soap.service.GameResponse;
import no.nith.pg6100.soap.service.TeamResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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

        verifyZeroInteractions(teamResponse);
    }

    @Test
    public void testGetTeams() throws Exception {
        eSportServiceWebService.getTeams("halla");

        verify(esportService).getTeams("123", "halla");
        verify(teamResponse).getTeams();

        verifyNoMoreInteractions(esportService, teamResponse);

        verifyZeroInteractions(gameResponse);
    }
}
