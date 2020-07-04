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
import data.Disk;
import data.Korisnik;
import data.Korisnik.Uloga;
import data.Organizacija;
import dto.DiskDTO;

@Path("/diskovi")
public class DiskService {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDiskove(@Context HttpServletRequest request) {
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
		List<DiskDTO> diskoviDTO = new ArrayList<>();
		for (Disk disk : AllLists.diskovi.getByOrganization(org)) {
			diskoviDTO.add(new DiskDTO(disk));
		}
		return Response.ok(diskoviDTO).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makeDisk(DiskDTO disk) {
		if(!AllLists.diskovi.addDisk(disk)) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity(AllLists.diskovi.problemMsg)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		return Response
				.ok(disk)
				.build();
	}
	
	@GET
	@Path("{ime}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDisk(@PathParam("ime") String ime) {
		Disk disk = AllLists.diskovi.find(ime);
		if(disk == null) {
			Response
				.status(Status.BAD_REQUEST)
				.entity(AllLists.diskovi.problemMsg)
				.type(MediaType.APPLICATION_JSON)
				.build();
		}
		return Response
				.ok(new DiskDTO(disk))
				.build();
	}
	
	@PUT
	@Path("{ime}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response chnageDisk(@PathParam("ime") String ime, DiskDTO di) {
		if(!AllLists.diskovi.changeDisk(di, ime)) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity(AllLists.diskovi.problemMsg)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		return Response
				.ok(di)
				.build();
	}
	
	@DELETE
	@Path("{ime}")
	public Response deleteDisk(@PathParam("ime") String ime) {
		if(!AllLists.diskovi.deleteDisk(ime)) {
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

















