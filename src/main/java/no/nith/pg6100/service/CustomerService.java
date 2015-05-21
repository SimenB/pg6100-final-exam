package no.nith.pg6100.service;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import no.nith.pg6100.service.dto.Customer;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/customers")
@Api(value = "/customers", description = "Creates and gets customers")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerService {

    @Context
    private UriInfo uriInfo;

    @Inject
    private Customers customers;

    @GET
    @Path("{index}")
    @ApiOperation(value = "Get the customer based on the index", response = Customer.class)
    public Response getCustomer(@PathParam("index") int index) {
        return Response.ok(customers.get(index)).build();
    }

    @GET
    @ApiOperation(value = "Get all registered customers", response = Customer.class)
    public Response getAll() {
        return Response.ok(customers.getAll()).build();
    }

    @PUT
    @Path("{name}")
    @ApiOperation(value = "Add a new customer", response = URI.class)
    public Response addCustomer(@PathParam("name") String name) {
        int index = customers.add(new Customer(name));
        URI uri = uriInfo.getAbsolutePathBuilder().path("/customers/" + index).build();
        return Response.created(uri).build();
    }
}
