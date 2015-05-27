package no.nith.pg6100.service;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import no.nith.pg6100.entity.Result;
import no.nith.pg6100.infrastructure.ESportServiceWebService;
import no.nith.pg6100.infrastructure.ResultDao;
import no.nith.pg6100.soap.service.Team;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collections;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Path("/esports")
@Api(value = "/esports", description = "operations on esports")
public class ESportService {
    @Context
    private UriInfo uriInfo;

    @Inject
    private ESportServiceWebService eSportServiceWebService;

    @Inject
    private ResultDao resultDao;

    @GET
    @Path("/games")
    @ApiOperation(value = "Finds all games",
        response = String.class,
        responseContainer = "List")
    public Response getAllGames() {
        final List<String> games = eSportServiceWebService.getGames().orElseGet(Collections::emptyList);

        return Response.ok(games).build();
    }

    @GET
    @Path("/teams/{game}")
    @ApiOperation(value = "Finds all teams playing a given game",
        response = Team.class,
        responseContainer = "List")
    public Response getAllTeams(@PathParam("game") final String game) {
        final List<Team> teams = eSportServiceWebService.getTeams(game).orElseGet(Collections::emptyList);

        return Response.ok(teams).build();
    }

    @POST
    @Path("/results")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create new Result",
        response = Result.class)
    public Response saveResult(@Valid final Result result) {
        resultDao.persist(result);
        final URI uri = uriInfo.getAbsolutePathBuilder().path("/customer/" + (resultDao.getTotalNumberOfResults() - 1)).build();

        return Response.created(uri).entity(result).build();
    }

    @GET
    @Path("/results/{id}")
    @ApiOperation(value = "Finds the result",
        response = Result.class)
    public Response getResultsById(@PathParam("id") final int id) {
        return Response.ok(resultDao.findById(id)).build();
    }

    @GET
    @Path("/results")
    @ApiOperation(value = "Finds all results for a team",
        response = Result.class,
        responseContainer = "List")
    public Response getResultsForTeam(@QueryParam("team") final int teamId) {
        return Response.ok(resultDao.findAllByTeam(teamId)).build();
    }
}
