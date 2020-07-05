package services;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
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

@Path("/images")
public class ImageService {

	@GET
	@Path("{name}")
	@Produces("image/jpg")
	public Response getImage(@Context HttpServletRequest request, @PathParam("name") String name) {
		if(!Proveravator.proveriUlogu(new Uloga[] {Uloga.SUPER_ADMIN, Uloga.ADMIN}, request)) {
			return Response
					.status(Status.UNAUTHORIZED)
					.entity("Not logged in or insufficient privileges!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		Korisnik korisnik = (Korisnik)request.getSession().getAttribute(LoginService.korisnikAttr);
		if(korisnik.getUloga() == Uloga.ADMIN && !korisnik.getOrganizacija().getLogo().equals(name)) {
			return Response
					.status(Status.UNAUTHORIZED)
					.entity("Not logged in or insufficient privileges!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		File file = new File(AllLists.pictureFolder + File.separator + name);
		return Response
				.ok(file, "image/jpg").header("Inline", "filename=\"" + file.getName() + "\"").build();
	}
}
