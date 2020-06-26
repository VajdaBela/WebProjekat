package app;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import data.Korisnik;
import data.Korisnik.Uloga;
import data.Organizacija;

@ApplicationPath("/rest")
public class WebApp extends Application {
	public WebApp() {
		Organizacija organizacija1 = new Organizacija("org1", "jako dobar");
		Organizacija organizacija2 = new Organizacija("org2", "vrlo los");
		AllLists.organizacije.getOrganizacije().add(organizacija1);
		AllLists.organizacije.getOrganizacije().add(organizacija2);
		
		
		Korisnik korisnik1 = new Korisnik("admin", "admin", "admin", "admin", null, Uloga.SUPER_ADMIN);
		Korisnik korisnik2 = new Korisnik("korisnik2", "korisnik2", "korisnik2", "korisnik2", organizacija1, Uloga.ADMIN);
		Korisnik korisnik3 = new Korisnik("korisnik3", "korisnik3", "korisnik3", "korisnik3", organizacija1, Uloga.KORISNIK);
		Korisnik korisnik4 = new Korisnik("korisnik1", "korisnik1", "korisnik1", "korisnik1", organizacija2, Uloga.KORISNIK);
		AllLists.korisnici.getKorisnici().add(korisnik1);
		AllLists.korisnici.getKorisnici().add(korisnik2);
		AllLists.korisnici.getKorisnici().add(korisnik3);
		AllLists.korisnici.getKorisnici().add(korisnik4);
		
		
		organizacija1.getKorisnici().add(korisnik2);
		organizacija1.getKorisnici().add(korisnik3);
		organizacija2.getKorisnici().add(korisnik4);
		
	}
}
