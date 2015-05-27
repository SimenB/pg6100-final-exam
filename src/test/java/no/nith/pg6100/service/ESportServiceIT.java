package no.nith.pg6100.service;

import no.nith.pg6100.entity.Result;
import no.nith.pg6100.soap.service.Team;
import org.jboss.resteasy.util.HttpResponseCodes;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static no.nith.pg6100.Constants.BASE_URL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

public class ESportServiceIT {
    private static final String URL = BASE_URL + "/esports";
    private static final GenericType<List<String>> LIST_OF_STRINGS = new GenericType<List<String>>() {
    };
    private static final GenericType<List<Team>> LIST_OF_TEAMS = new GenericType<List<Team>>() {
    };
    private static final GenericType<List<Result>> LIST_OF_RESULTS = new GenericType<List<Result>>() {
    };

    private Client client;

    @Before
    public void setUp() throws Exception {
        // TODO: The external service really should be mocked
        client = ClientBuilder.newClient();
    }

    @Test
    public void testGetGames() {
        final Response response = client
            .target(URL + "/games")
            .request()
            .get();

        assertThat(response.getStatus(), is(HttpResponseCodes.SC_OK));

        final List<String> games = response.readEntity(LIST_OF_STRINGS);

        assertThat(games, hasSize(2));
        assertThat(games.get(0), is("Hearthstone"));
        assertThat(games.get(1), is("League of Legends"));
    }

    @Test
    public void testGetTeamsPlayingAGame() {
        final Response response = client
            .target(URL + "/teams/Hearthstone")
            .request()
            .get();

        assertThat(response.getStatus(), is(HttpResponseCodes.SC_OK));

        final List<Team> teams = response.readEntity(LIST_OF_TEAMS);
        assertThat(teams, hasSize(2));

        assertThat(teams.get(0).getId(), is(9));
        assertThat(teams.get(0).getName(), is("BX3 Elektroniske Sportsklubb HS"));
        assertThat(teams.get(1).getId(), is(10));
        assertThat(teams.get(1).getName(), is("GamersLeague eSport HS"));
    }

    @Test
    public void testGetResult() {
        final Response response = client
            .target(URL + "/results/9")
            .request()
            .get();

        assertThat(response.getStatus(), is(HttpResponseCodes.SC_OK));

        final Result result = response.readEntity(new GenericType<>(Result.class));

        assertThat(result.getId(), is(9));
        assertThat(result.getWinner(), is(9));
        assertThat(result.getLoser(), is(10));
    }

    @Test
    public void testGetResultsForTeam() {
        final Response response = client
            .target(URL + "/results?team=2")
            .request()
            .get();

        assertThat(response.getStatus(), is(HttpResponseCodes.SC_OK));

        final List<Result> results = response.readEntity(LIST_OF_RESULTS);

        assertThat(results, hasSize(3));

        assertThat(results.get(0).getId(), is(1));
        assertThat(results.get(0).getWinner(), is(2));
        assertThat(results.get(0).getLoser(), is(1));
        assertThat(results.get(1).getId(), is(2));
        assertThat(results.get(1).getWinner(), is(1));
        assertThat(results.get(1).getLoser(), is(2));
        assertThat(results.get(2).getId(), is(3));
        assertThat(results.get(2).getWinner(), is(1));
        assertThat(results.get(2).getLoser(), is(2));
    }

    @Test
    public void testPersistResult() {
        final Result result = new Result();
        result.setWinner(1);
        result.setLoser(2);

        final Response response = client
            .target(URL + "/results")
            .request()
            .post(Entity.entity(result, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(HttpResponseCodes.SC_CREATED, response.getStatus());
    }

    @Test
    public void testPersistInvalidResult() {
        final Result result = new Result();

        final Response response = client
            .target(URL + "/results")
            .request()
            .post(Entity.entity(result, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(HttpResponseCodes.SC_BAD_REQUEST, response.getStatus());
    }
}
