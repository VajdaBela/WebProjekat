package services;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import app.AllLists;

@Path("/demo")
public class TestService {

	@Context ServletContext context;
	
	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public String test() {
		System.out.println(context.getRealPath("/"));
		AllLists.korisnici.getKorisnici();
		AllLists.organizacije.getOrganizacije();
		AllLists.virtualneMasine.getVirtualneMasine();
		AllLists.diskovi.getDiskovi();
		AllLists.kategorije.getKategorije();
		return "REST rulez!";
	}
}
