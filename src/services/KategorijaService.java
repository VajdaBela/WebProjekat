package services;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import app.AllLists;
import data.Kategorija;

@Path("/kategorije")
public class KategorijaService {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getKategorije() {
		return Response
				.ok(AllLists.kategorije.getKategorije())
				.build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makeKategorija(Kategorija kat) {
		if(!AllLists.kategorije.addKategorija(kat)) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity(AllLists.kategorije.problemMsg)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		return Response
				.ok(kat)
				.build();
	}
	
	@GET
	@Path("{ime}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getKategorija(@PathParam("ime") String ime) {
		Kategorija kat = AllLists.kategorije.find(ime);
		if(kat == null) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity(AllLists.kategorije.problemMsg)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		return Response
				.ok(kat)
				.build();
	}
	
	@PUT
	@Path("{ime}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeKategorija(@PathParam("ime") String ime, Kategorija kat) {
		if(!AllLists.kategorije.changeKategorija(kat, ime)) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity(AllLists.kategorije.problemMsg)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		return Response
				.ok(kat)
				.build();
	}
	
	@DELETE
	@Path("{ime}")
	public Response deleteKategorija(@PathParam("ime") String ime) {
		if(!AllLists.kategorije.deleteKategorija(ime)) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity(AllLists.kategorije.problemMsg)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		return Response
				.ok()
				.build();
	}
}











