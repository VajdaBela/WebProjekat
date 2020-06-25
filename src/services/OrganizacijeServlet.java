package services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import app.AllLists;
import data.Organizacija;

@Path("/organizacije")
public class OrganizacijeServlet {

	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrganizacije() {
		return Response
				.ok(AllLists.organizacije.getOrganizacije())
				.build();
	}

	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makeOrganizacija(Organizacija org) {
		if(!AllLists.organizacije.addOrganizacija(org)) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(AllLists.organizacije.problemMsg)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		return Response
				.ok(org)
				.build();
	}
	
	@GET
	@Path("{ime}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrganizacija(@PathParam("ime") String ime) {
		if(!AllLists.organizacije.find(ime)) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(AllLists.organizacije.problemMsg)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		return Response
				.ok(AllLists.organizacije.found)
				.build();
	}
	
	@PUT
	@Path("{ime}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeOrganization(@PathParam("ime") String ime, Organizacija org) {
		if(!AllLists.organizacije.changeOrganizacija(org, ime)) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(AllLists.organizacije.problemMsg)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		return Response
				.ok(AllLists.organizacije.found)
				.build();
	}
}
