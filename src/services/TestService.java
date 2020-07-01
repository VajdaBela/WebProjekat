package services;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import app.AllLists;
import data.Korisnik;

@Path("/demo")
public class TestService {

	@Context ServletContext context;
	
	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public String test(@Context HttpServletRequest request) {
		Korisnik korisnik = (Korisnik)request.getSession().getAttribute("korisnik");
		System.out.println(context.getRealPath("/"));
		AllLists.korisnici.getKorisnici();
		AllLists.organizacije.getOrganizacije();
		AllLists.virtualneMasine.getVirtualneMasine();
		AllLists.diskovi.getDiskovi();
		AllLists.kategorije.getKategorije();
		return "REST rulez!";
	}
}
