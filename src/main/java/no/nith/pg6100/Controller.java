package no.nith.pg6100;

import no.nith.pg6100.domain.GameResult;
import no.nith.pg6100.domain.TeamWithResults;
import no.nith.pg6100.entity.Result;
import no.nith.pg6100.infrastructure.ESportServiceWebService;
import no.nith.pg6100.infrastructure.ResultDao;
import no.nith.pg6100.soap.service.Team;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Model
public class Controller {

    @Inject
    private ESportServiceWebService eSportServiceWebService;

    @Inject
    private ResultDao resultDao;

    @Inject
    private FacesContext facesContext;

    public List<GameResult> getGameResults() {
        return eSportServiceWebService
            .getGames()
            .stream()
            .map(game -> {
                final List<TeamWithResults> teamWithResults = eSportServiceWebService
                    .getTeams(game)
                    .stream()
                    .map(getTeamWithResultsFromTeam())
                    .sorted(Comparator.comparingLong(TeamWithResults::getNumOfVictories).reversed())
                    .collect(Collectors.toList());

                return new GameResult(game, teamWithResults);
            })
            .collect(Collectors.toList());
    }

    private Function<Team, TeamWithResults> getTeamWithResultsFromTeam() {
        return team -> {
            final List<Result> results = resultDao.findAllByTeam(team.getId());
            final long wins = results.stream().filter(result -> result.getWinner() == team.getId()).count();
            final long losses = results.stream().filter(result -> result.getLoser() == team.getId()).count();

            return new TeamWithResults(team.getId(), team.getName(), wins, losses);
        };
    }

    public String logout() {
        facesContext.getExternalContext().invalidateSession();

        return "/login.xhtml";
    }
}
