package services;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import app.AllLists;
import data.Korisnik;
import data.Korisnik.Uloga;

@Path("/login")
public class LoginService {

	public static String korisnikAttr = "korisnik";
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@Context HttpServletRequest request, HashMap<String , String> credentials) {
		Korisnik korisnik = (Korisnik)request.getSession().getAttribute(korisnikAttr);
		
		if(korisnik != null) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity("Already logged in!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		
		Korisnik tempKorisnik = AllLists.korisnici.getKorisnici().get(credentials.get("name"));
		if(tempKorisnik != null && tempKorisnik.getLozinka().equals(credentials.get("password"))) {
			request.getSession().setAttribute(korisnikAttr, tempKorisnik);
			return Response
					.ok()
					.build();
		}
		
		return Response
				.status(Status.BAD_REQUEST)
				.entity("Bad credentials!")
				.type(MediaType.APPLICATION_JSON)
				.build();
//		HttpSession session = request.getSession(true);
//		
//		Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");
//
//		if(action.equals("off")) {
//			session.invalidate();
//			//TODO korisnik nazin ne sme -1
//			return Response
//					.ok()
//					.cookie(new NewCookie("korisnik", "-1"))
//					.build();
//		}
//		
//		String userIdentificationKey = null;
//		Cookie cookies[] = request.getCookies();
//		if(cookies != null) {
//			for(int i = 0; i < cookies.length; i++) {
//				if(cookies[i].getName().equals("korisnik")) {
//					userIdentificationKey = cookies[i].getValue();
//					break;
//				}
//			}
//		}
//		
//		if(korisnik == null && userIdentificationKey != null && !userIdentificationKey.equals("-1")) {
//			korisnik = AllLists.korisnici.getKorisnici().get(userIdentificationKey);
//		}
//		
//		if(korisnik == null && credentials != null) {
//			Korisnik tempKorisnik = AllLists.korisnici.getKorisnici().get(credentials.get("name"));
//			
//			if(tempKorisnik != null && tempKorisnik.getLozinka().equals(credentials.get("password"))) {
//				korisnik = tempKorisnik;
//			}
//		}
//		
//		if(korisnik == null) {
//			return Response
//					.status(Status.BAD_REQUEST)
//					.entity(Uloga.NEULOGOVAN)
//					.type(MediaType.APPLICATION_JSON)
//					.build();
//		}
//		else {
//			session.setAttribute("korisnik", korisnik);
//			return Response
//					.ok()
//					.cookie(new NewCookie("korisnik", korisnik.getEmail()))
//					.entity(korisnik.getUloga())
//					.type(MediaType.APPLICATION_JSON)
//					.build();
//		}

	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response isLoggedIn(@Context HttpServletRequest request) {
		Korisnik korisnik = (Korisnik) request.getSession().getAttribute(korisnikAttr);
		Uloga uloga = null;
		if(korisnik != null) {
			uloga = korisnik.getUloga();
		}
		else {
			uloga = Uloga.NEULOGOVAN;
		}
		return Response
				.ok(uloga)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
	
	@GET
	@Path("/off")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(@Context HttpServletRequest request) {
		request.getSession().invalidate();
		return Response
				.ok()
				.build();
	}

}
