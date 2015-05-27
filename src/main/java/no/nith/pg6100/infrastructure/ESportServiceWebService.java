package no.nith.pg6100.infrastructure;

import no.nith.pg6100.soap.service.Team;

import java.util.List;
import java.util.Optional;

public interface ESportServiceWebService {
    Optional<List<String>> getGames();
    Optional<List<Team>> getTeams(String game);
}
