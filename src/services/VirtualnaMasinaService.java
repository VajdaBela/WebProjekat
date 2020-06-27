package services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import app.AllLists;
import data.Organizacija;
import data.VirtualnaMasina;
import dto.VirtualnaMasinaDTO;

@Path("/virtualneMasine")
public class VirtualnaMasinaService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVirtualnaMasinaByOrganizacija(@QueryParam("ime") String organizacija) {
		Organizacija org;
		if (organizacija == null) {
			org = null;
		} else {
			org = AllLists.organizacije.find(organizacija);
		}

		if (org == null && organizacija != null) {
			return Response.status(Status.BAD_REQUEST).entity(AllLists.organizacije.problemMsg)
					.type(MediaType.APPLICATION_JSON).build();
		}
		List<VirtualnaMasinaDTO> virtualneMasineDto = new ArrayList<>();
		for (VirtualnaMasina virtualnaMasina : AllLists.virtualneMasine.getByOrganization(org)) {
			virtualneMasineDto.add(new VirtualnaMasinaDTO(virtualnaMasina));
		}
		return Response.ok(virtualneMasineDto).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makeVirtualnaMasina(VirtualnaMasinaDTO virm) {
		if (!AllLists.virtualneMasine.addVirtualnaMasina(virm)) {
			return Response.status(Status.BAD_REQUEST).entity(AllLists.virtualneMasine.problemMsg)
					.type(MediaType.APPLICATION_JSON).build();
		}
		return Response.ok(virm).build();
	}

	@GET
	@Path("{ime}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVirtualnaMasina(@PathParam("ime") String ime) {
		VirtualnaMasina virtualnaMasina = AllLists.virtualneMasine.find(ime);
		if (virtualnaMasina == null) {
			return Response.status(Status.BAD_REQUEST).entity(AllLists.virtualneMasine.problemMsg)
					.type(MediaType.APPLICATION_JSON).build();
		}
		return Response.ok(new VirtualnaMasinaDTO(virtualnaMasina)).build();
	}

	@PUT
	@Path("{ime}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeVirtualnaMasina(@PathParam("ime") String ime, VirtualnaMasinaDTO virm) {
		if (!AllLists.virtualneMasine.changeVirtualnaMasina(virm, ime)) {
			return Response.status(Status.BAD_REQUEST).entity(AllLists.virtualneMasine.problemMsg)
					.type(MediaType.APPLICATION_JSON).build();
		}
		return Response.ok(virm).build();
	}

	@DELETE
	@Path("{ime}")
	public Response deleteVirtualnaMasina(@PathParam("ime") String ime) {
		if (!AllLists.virtualneMasine.deleteVirtualnaMasina(ime)) {
			return Response.status(Status.BAD_REQUEST).entity(AllLists.virtualneMasine.problemMsg)
					.type(MediaType.APPLICATION_JSON).build();
		}
		return Response.ok().build();
	}
}
