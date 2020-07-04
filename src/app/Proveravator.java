package app;

import javax.servlet.http.HttpServletRequest;

import data.Korisnik;
import data.Korisnik.Uloga;
import services.LoginService;

public class Proveravator {
	public static boolean proveriUlogu(Uloga dozvoljene[], HttpServletRequest request) {
		Korisnik korisnik = (Korisnik)request.getSession().getAttribute(LoginService.korisnikAttr);
		if(korisnik == null) {
			return false;
		}
		for(int i = 0; i < dozvoljene.length; i++) {
			if(korisnik.getUloga() == dozvoljene[i]) {
				return true;
			}
		}
		return false;
	}
}
