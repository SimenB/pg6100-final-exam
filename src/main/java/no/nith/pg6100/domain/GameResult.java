package no.nith.pg6100.domain;

import java.util.List;

public class GameResult {
    private final String game;
    private final List<TeamWithResults> teams;

    public GameResult(final String game, final List<TeamWithResults> teams) {
        this.game = game;
        this.teams = teams;
    }

    public String getGame() {
        return game;
    }

    public List<TeamWithResults> getTeams() {
        return teams;
    }
}
