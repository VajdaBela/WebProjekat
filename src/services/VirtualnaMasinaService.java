package services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import app.AllLists;
import app.Proveravator;
import data.Korisnik;
import data.Korisnik.Uloga;
import data.Organizacija;
import data.VirtualnaMasina;
import dto.VirtualnaMasinaDTO;

@Path("/virtualneMasine")
public class VirtualnaMasinaService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVirtualnaMasinaByOrganizacija(@Context HttpServletRequest request) {
		Korisnik korisnik = (Korisnik)request.getSession().getAttribute("korisnik");
		if(!Proveravator.proveriUlogu(new Uloga[] {Uloga.SUPER_ADMIN, Uloga.ADMIN, Uloga.KORISNIK}, request)) {
			return Response
					.status(Status.UNAUTHORIZED)
					.entity("Not logged in or insufficient privileges!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		
		Organizacija org;
		if(korisnik.getUloga() == Uloga.SUPER_ADMIN) {
			org = null;
		}
		else {
			org = korisnik.getOrganizacija();
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
