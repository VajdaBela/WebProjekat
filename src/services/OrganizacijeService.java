package services;

import java.util.ArrayList;
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
import dto.OrganizacijaDTO;

@Path("/organizacije")
public class OrganizacijeService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrganizacije() {
		List<OrganizacijaDTO> organizacijeDto = new ArrayList<>();
		for (Organizacija organizacija : AllLists.organizacije.getOrganizacije().values()) {
			organizacijeDto.add(new OrganizacijaDTO(organizacija));
		}
		return Response
				.ok(organizacijeDto)
				.build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makeOrganizacija(OrganizacijaDTO org) {
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
		Organizacija org = AllLists.organizacije.find(ime);
		if(org == null) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(AllLists.organizacije.problemMsg)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		return Response
				.ok(new OrganizacijaDTO(org))
				.build();
	}
	
	@PUT
	@Path("{ime}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeOrganization(@PathParam("ime") String ime, OrganizacijaDTO org) {
		if(!AllLists.organizacije.changeOrganizacija(org, ime)) {
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
}
