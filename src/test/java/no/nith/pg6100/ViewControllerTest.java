package no.nith.pg6100;

import no.nith.pg6100.domain.GameResult;
import no.nith.pg6100.infrastructure.ESportServiceWebService;
import no.nith.pg6100.service.ViewController;
import no.nith.pg6100.soap.service.Team;
import no.nith.pg6100.util.MappingFunctions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ViewControllerTest {
    @Mock
    private ESportServiceWebService eSportServiceWebService;
    @Mock
    private MappingFunctions mappingFunctions;
    @Mock
    private FacesContext facesContext;

    @InjectMocks
    private ViewController objectUnderTest;

    @Before
    public void setUp() throws Exception {
        when(eSportServiceWebService.getGames()).thenReturn(Optional.<List<String>>empty());
        when(eSportServiceWebService.getTeams(anyString())).thenReturn(Optional.<List<Team>>empty());
    }

    @Test
    public void testLogout() throws Exception {
        final ExternalContext externalContext = mock(ExternalContext.class);
        when(facesContext.getExternalContext()).thenReturn(externalContext);

        final String result = objectUnderTest.logout();

        assertThat(result, is("/login.xhtml"));

        verify(facesContext).getExternalContext();
        verify(externalContext).invalidateSession();

        verifyNoMoreInteractions(facesContext, externalContext);
        verifyZeroInteractions(eSportServiceWebService, mappingFunctions);
    }

    @Test
    public void testGeGameResults() throws Exception {
        when(eSportServiceWebService.getGames()).thenReturn(Optional.empty());
        when(mappingFunctions.mapTeamsToGameWithResults()).thenReturn(s -> mock(GameResult.class));

        objectUnderTest.getGameResults();

        verify(eSportServiceWebService).getGames();
        verify(mappingFunctions).mapTeamsToGameWithResults();

        verifyNoMoreInteractions(eSportServiceWebService, mappingFunctions);
        verifyZeroInteractions(facesContext);
    }
}
