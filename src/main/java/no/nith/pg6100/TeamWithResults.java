package no.nith.pg6100;

public class TeamWithResults {
    private int id;
    private String name;
    private long numOfVictories;
    private long numOfLosses;

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
