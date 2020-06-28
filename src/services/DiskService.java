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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import app.AllLists;
import data.Disk;
import dto.DiskDTO;

@Path("/diskovi")
public class DiskService {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDiskove() {
		//TODO fix by organization
		List<DiskDTO> diskoviDTO = new ArrayList<>();
		for (Disk disk : AllLists.diskovi.getDiskovi().values()) {
			diskoviDTO.add(new DiskDTO(disk));
		}
		return Response
				.ok(diskoviDTO)
				.build();
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

















