package no.nith.pg6100.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TeamWithResultsTest {
    private TeamWithResults teamWithResults;

    @Before
    public void setUp() throws Exception {
        teamWithResults = new TeamWithResults(1, "name", 2, 5);
    }

    @Test
    public void testGetTotalNumOfMatches() throws Exception {
        assertThat(teamWithResults.getTotalNumOfMatches(), is(7));
    }
}
