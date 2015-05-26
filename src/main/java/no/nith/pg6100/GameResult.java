package no.nith.pg6100;

import java.util.List;

public class GameResult {
    private String game;
    private List<TeamWithResults   > teams;

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
