package no.nith.pg6100;

import no.nith.pg6100.domain.GameResult;
import no.nith.pg6100.infrastructure.ESportServiceWebService;
import no.nith.pg6100.util.MappingFunctions;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Model
public class Controller {

    @Inject
    private ESportServiceWebService eSportServiceWebService;

    @Inject
    private MappingFunctions mappingFunctions;

    @Inject
    private FacesContext facesContext;

    public List<GameResult> getGameResults() {
        return eSportServiceWebService
            .getGames()
            .stream()
            .map(mappingFunctions.mapTeamsToGameWithResults())
            .collect(Collectors.toList());
    }

    public String logout() {
        facesContext.getExternalContext().invalidateSession();

        return "/login.xhtml";
    }
}
