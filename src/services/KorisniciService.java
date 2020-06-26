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
import data.Korisnik;
import data.Organizacija;
import dto.KorisnikDTO;

@Path("/korisnici")
public class KorisniciService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getKorisnikByOrganizacija(@QueryParam("ime") String organizacijaIme) {
		Organizacija org;
		if (organizacijaIme == null) {
			org = null;
		} else {
			org = AllLists.organizacije.find(organizacijaIme);
		}

		if (org == null && organizacijaIme != null) {
			return Response.status(Status.BAD_REQUEST).entity(AllLists.organizacije.problemMsg).build();
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
	public Response makeKorisnik(KorisnikDTO kor) {
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
	public Response changeKorisnik(@PathParam("email") String email, KorisnikDTO kor) {
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
	public Response deleteKorisnik(@PathParam("email") String email) {
		if(!AllLists.korisnici.deleteKorisnik(email)) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity(AllLists.korisnici.problemMsg)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		return Response.ok().build();
	}
}
