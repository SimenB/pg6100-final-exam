package no.nith.pg6100.util;

import no.nith.pg6100.domain.GameResult;
import no.nith.pg6100.domain.TeamWithResults;
import no.nith.pg6100.entity.Result;
import no.nith.pg6100.infrastructure.ESportServiceWebService;
import no.nith.pg6100.infrastructure.ResultDao;
import no.nith.pg6100.soap.service.Team;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Stateless
public class MappingFunctions {
    @Inject
    private ESportServiceWebService eSportServiceWebService;
    @Inject
    private ResultDao resultDao;

    public Function<String, GameResult> mapTeamsToGameWithResults() {
        // Getting all results beforehand makes it one trip into the db, and not the same amount as number of teams
        final List<Result> results = resultDao.findAll();

        return game -> {
            final List<TeamWithResults> teamWithResults = eSportServiceWebService
                .getTeams(game)
                .stream()
                .map(getTeamWithResultsFromTeam(results))
                .sorted(Comparator.comparingLong(TeamWithResults::getNumOfVictories).reversed())
                .collect(Collectors.toList());

            return new GameResult(game, teamWithResults);
        };
    }

    private Function<Team, TeamWithResults> getTeamWithResultsFromTeam(final List<Result> resuls) {
        return team -> {
            final List<Result> resultsForTeam = resuls
                .stream()
                .filter(result -> result.getLoser() == team.getId() || result.getWinner() == team.getId())
                .collect(Collectors.toList());

            final long wins = resultsForTeam.stream().filter(result -> result.getWinner() == team.getId()).count();
            final long losses = resultsForTeam.stream().filter(result -> result.getLoser() == team.getId()).count();

            return new TeamWithResults(team.getId(), team.getName(), wins, losses);
        };
    }
}
