package services;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import app.AllLists;
import data.Korisnik;
import data.Korisnik.Uloga;

@Path("/login")
public class LoginService {

	@POST
	@Path("/{action}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@PathParam("action") String action, @Context HttpServletRequest request, HashMap<String , String> credentials) {
		HttpSession session = request.getSession(true);
//		String some = (String) sesion.getAttribute("e");
//		sesion.setAttribute("e", "asd");
		
		Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

		if(action.equals("off")) {
			session.invalidate();
			//TODO korisnik nazin ne sme -1
			return Response
					.ok()
					.cookie(new NewCookie("korisnik", "-1"))
					.build();
		}
		
		String userIdentificationKey = null;
		Cookie cookies[] = request.getCookies();
		if(cookies != null) {
			for(int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().equals("korisnik")) {
					userIdentificationKey = cookies[i].getValue();
					break;
				}
			}
		}
		
		if(korisnik == null && userIdentificationKey != null) {
			korisnik = AllLists.korisnici.getKorisnici().get(userIdentificationKey);
		}
		
		if(korisnik == null && credentials != null) {
			Korisnik tempKorisnik = AllLists.korisnici.getKorisnici().get(credentials.get("name"));
			
			if(tempKorisnik != null && tempKorisnik.getLozinka().equals(credentials.get("password"))) {
				korisnik = tempKorisnik;
			}
		}
		
		if(korisnik == null) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity(Uloga.NEULOGOVAN)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		else {
			session.setAttribute("korisnik", korisnik);
			return Response
					.ok()
					.cookie(new NewCookie("korisnik", korisnik.getEmail()))
					.entity(korisnik.getUloga())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}

	}

}
