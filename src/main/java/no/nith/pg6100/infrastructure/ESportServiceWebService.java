package no.nith.pg6100.infrastructure;

import no.nith.pg6100.soap.service.Team;

import java.util.List;

public interface ESportServiceWebService {
    List<String> getGames();
    List<Team> getTeams(String game);
}
