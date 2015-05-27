package no.nith.pg6100.domain;

public class TeamWithResults {
    private final int id;
    private final String name;
    private final long numOfVictories;
    private final long numOfLosses;

    public TeamWithResults(final int id, final String name, final long numOfVictories, final long numOfLosses) {
        this.id = id;
        this.name = name;
        this.numOfVictories = numOfVictories;
        this.numOfLosses = numOfLosses;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getNumOfVictories() {
        return numOfVictories;
    }

    public long getNumOfLosses() {
        return numOfLosses;
    }

    public long getTotalNumOfMatches() {
        return numOfLosses + numOfVictories;
    }
}
