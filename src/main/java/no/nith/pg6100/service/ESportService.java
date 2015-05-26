package no.nith.pg6100.service;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import no.nith.pg6100.infrastructure.ESportServiceWebService;
import no.nith.pg6100.soap.service.Team;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Path("/esports")
@Api(value = "/esports", description = "operations on esports")
public class ESportService {
    @Inject
    private ESportServiceWebService eSportServiceWebService;

    @GET
    @Path("/games")
    @ApiOperation(value = "Finds all games",
        response = String.class,
        responseContainer = "List")
    public Response getAllGames() {
        return Response.ok(eSportServiceWebService.getGames()).build();
    }

    @GET
    @Path("/teams/{game}")
    @ApiOperation(value = "Finds all games",
        response = Team.class,
        responseContainer = "List")
    public Response getAllTeams(@PathParam("game") final String game) {
        return Response.ok(eSportServiceWebService.getTeams(game)).build();
    }
}
