package services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
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
import dto.OrganizacijaDTO;

@Path("/organizacije")
public class OrganizacijeService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrganizacije(@Context HttpServletRequest request) {
		if(!Proveravator.proveriUlogu(new Uloga[] {Uloga.SUPER_ADMIN}, request)) {
			return Response
					.status(Status.UNAUTHORIZED)
					.entity("Not logged in or insufficient privileges!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
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
	public Response makeOrganizacija(@Context HttpServletRequest request, OrganizacijaDTO org) {
		if(!Proveravator.proveriUlogu(new Uloga[] {Uloga.SUPER_ADMIN}, request)) {
			return Response
					.status(Status.UNAUTHORIZED)
					.entity("Not logged in or insufficient privileges!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
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
	public Response getOrganizacija(@Context HttpServletRequest request, @PathParam("ime") String ime) {
		if(!Proveravator.proveriUlogu(new Uloga[] {Uloga.SUPER_ADMIN, Uloga.ADMIN}, request)) {
			return Response
					.status(Status.UNAUTHORIZED)
					.entity("Not logged in or insufficient privileges!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		Korisnik korisnik = (Korisnik)request.getSession().getAttribute(LoginService.korisnikAttr);
		if(korisnik.getUloga() == Uloga.ADMIN && !korisnik.getOrganizacija().getIme().equals(ime)) {
			return Response
					.status(Status.UNAUTHORIZED)
					.entity("Not logged in or insufficient privileges!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
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
	public Response changeOrganization(@Context HttpServletRequest request, @PathParam("ime") String ime, OrganizacijaDTO org) {
		if(!Proveravator.proveriUlogu(new Uloga[] {Uloga.SUPER_ADMIN, Uloga.ADMIN}, request)) {
			return Response
					.status(Status.UNAUTHORIZED)
					.entity("Not logged in or insufficient privileges!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		Korisnik korisnik = (Korisnik)request.getSession().getAttribute(LoginService.korisnikAttr);
		if(korisnik.getUloga() == Uloga.ADMIN && !korisnik.getOrganizacija().getIme().equals(ime)) {
			return Response
					.status(Status.UNAUTHORIZED)
					.entity("Not logged in or insufficient privileges!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
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
	
	@GET
	@Path("my")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMyOrganizacija(@Context HttpServletRequest request) {
		if(!Proveravator.proveriUlogu(new Uloga[] {Uloga.ADMIN}, request)) {
			return Response
					.status(Status.UNAUTHORIZED)
					.entity("Not logged in or insufficient privileges!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		Korisnik korisnik = (Korisnik)request.getSession().getAttribute(LoginService.korisnikAttr);
		return Response
				.ok(new OrganizacijaDTO(korisnik.getOrganizacija()))
				.build();
	}
}
