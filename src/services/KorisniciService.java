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
import dto.KorisnikDTO;

@Path("/korisnici")
public class KorisniciService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getKorisnikByOrganizacija(@Context HttpServletRequest request) {
		Korisnik ulogovanaikorisnik = (Korisnik)request.getSession().getAttribute("korisnik");
		if(!Proveravator.proveriUlogu(new Uloga[] {Uloga.SUPER_ADMIN, Uloga.ADMIN}, request)) {
			return Response
					.status(Status.UNAUTHORIZED)
					.entity("Not logged in or insufficient privileges!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		Organizacija org;
		if(ulogovanaikorisnik.getUloga() == Uloga.SUPER_ADMIN) {
			org = null;
		}
		else {
			org = ulogovanaikorisnik.getOrganizacija();
		}

		List<KorisnikDTO> korisniciDto = new ArrayList<>();
		for (Korisnik korisnik : AllLists.korisnici.getByOrganization(org)) {
			korisniciDto.add(new KorisnikDTO(korisnik));
		}
		return Response.ok(korisniciDto).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makeKorisnik(@Context HttpServletRequest request, KorisnikDTO kor) {
		Korisnik ulogovanaikorisnik = (Korisnik)request.getSession().getAttribute("korisnik");
		if(!Proveravator.proveriUlogu(new Uloga[] {Uloga.SUPER_ADMIN, Uloga.ADMIN}, request)) {
			return Response
					.status(Status.UNAUTHORIZED)
					.entity("Not logged in or insufficient privileges!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		if(ulogovanaikorisnik.getUloga() == Uloga.ADMIN) {
			kor.setOrganizacija(ulogovanaikorisnik.getOrganizacija().getIme());
		}
		if(!AllLists.korisnici.addKorisnik(kor)) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity(AllLists.korisnici.problemMsg)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		return Response
				.ok(kor)
				.build();
	}
	
	@GET
	@Path("{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getKorisnik(@PathParam("email") String email) {
		Korisnik korisnik = AllLists.korisnici.find(email);
		if(korisnik == null) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity(AllLists.korisnici.problemMsg)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		return Response
				.ok(new KorisnikDTO(korisnik))
				.build();
	}
	
	@PUT
	@Path("{email}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeKorisnik(@Context HttpServletRequest request, @PathParam("email") String email, KorisnikDTO kor) {
		Korisnik ulogovanaikorisnik = (Korisnik)request.getSession().getAttribute("korisnik");
		if(!Proveravator.proveriUlogu(new Uloga[] {Uloga.SUPER_ADMIN, Uloga.ADMIN}, request)) {
			return Response
					.status(Status.UNAUTHORIZED)
					.entity("Not logged in or insufficient privileges!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		if(ulogovanaikorisnik.getUloga() == Uloga.ADMIN) {
			boolean dobar = false;
			for(Korisnik kori : ulogovanaikorisnik.getOrganizacija().getKorisnici().values()) {
				if(kori.getEmail().equals(email)) {
					dobar = true;
					break;
				}
			}
			if(!dobar) {
				return Response
						.status(Status.UNAUTHORIZED)
						.entity("Not logged in or insufficient privileges!")
						.type(MediaType.APPLICATION_JSON)
						.build();
			}
		}
		if(!AllLists.korisnici.changeKorisnik(kor, email)) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity(AllLists.korisnici.problemMsg)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		return Response
				.ok(kor)
				.build();
	}
	
	@DELETE
	@Path("{email}")
	public Response deleteKorisnik(@Context HttpServletRequest request, @PathParam("email") String email) {
		Korisnik korisnik = (Korisnik)request.getSession().getAttribute(LoginService.korisnikAttr);
		if(!Proveravator.proveriUlogu(new Uloga[] {Uloga.SUPER_ADMIN, Uloga.ADMIN}, request)) {
			return Response
					.status(Status.UNAUTHORIZED)
					.entity("Not logged in or insufficient privileges!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		if(korisnik.getEmail().equals(email)) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity("Cant delete self!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		boolean dobar = false;
		if(korisnik.getUloga() == Uloga.ADMIN) {
			for(Korisnik kor : korisnik.getOrganizacija().getKorisnici().values()) {
				if(kor.getEmail().equals(email)) {
					dobar = true;
					break;
				}
			}
			if(!dobar) {
				return Response
						.status(Status.UNAUTHORIZED)
						.entity("Not logged in or insufficient privileges!")
						.type(MediaType.APPLICATION_JSON)
						.build();
			}
		}
		if(!AllLists.korisnici.deleteKorisnik(email)) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity(AllLists.korisnici.problemMsg)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		return Response.ok().build();
	}
	
	@GET
	@Path("/sebe")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSebe(@Context HttpServletRequest request) {
		return Response.ok(new KorisnikDTO((Korisnik)request.getSession().getAttribute(LoginService.korisnikAttr))).build();
	}
	
	@PUT
	@Path("/sebe")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setSebe(@Context HttpServletRequest request, KorisnikDTO kor) {
		Korisnik korisnik = (Korisnik)request.getSession().getAttribute(LoginService.korisnikAttr);
		if(korisnik == null) {
			return Response
					.status(Status.UNAUTHORIZED)
					.entity("Not logged in or insufficient privileges!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		if(!AllLists.korisnici.changeSelf(kor, korisnik)) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity(AllLists.korisnici.problemMsg)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		return Response.ok(new KorisnikDTO(korisnik)).build();
	}
}
