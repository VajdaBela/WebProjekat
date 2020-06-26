package lists;

import java.util.ArrayList;
import java.util.List;

import app.AllLists;
import app.JsonError;
import data.Korisnik;
import data.Organizacija;
import data.Korisnik.Uloga;
import dto.KorisnikDTO;

public class KorisnikList {
	private List<Korisnik> korisnici = new ArrayList<>();
	public JsonError problemMsg = new JsonError();
	
	public List<Korisnik> getByOrganization(Organizacija organization) {
		if(organization == null) {
			return korisnici;
		}
		List<Korisnik> sameOrgKorisnici = new ArrayList<>();
		for (Korisnik korisnik : korisnici) {
			if(korisnik.getOrganizacija() == organization ) {
				sameOrgKorisnici.add(korisnik);
			}
		}
		return sameOrgKorisnici;
	}
	
	public boolean addKorisnik(KorisnikDTO kor) {
		if(kor.getEmail() == null || kor.getEmail().equals("")) {
			problemMsg.setError("Email mora da postolji!");
			return false;
		}
		if(find(kor.getEmail()) != null) {
			problemMsg.setError("Email nije jedinstven!");
			return false;
		}
		//TODO implement find
		
		if(kor.getIme() == null || kor.getIme().equals("")) {
			problemMsg.setError("Ime mora da postolji!");
			return false;
		}
		
		if(kor.getPrezime() == null || kor.getPrezime().equals("")) {
			problemMsg.setError("Prezime mora da postolji!");
			return false;
		}
		
		if(kor.getOrganizacija() == null) {
			problemMsg.setError("Organizacija mora da postolji!");
			return false;
		}
		Organizacija organizacija = AllLists.organizacije.find(kor.getOrganizacija());
		if(organizacija == null)
		{
			problemMsg.setError("Nepostolji organizacija!");
			return false;
		}
		
		if(kor.getUloga() == null || kor.getUloga() == "") {
			problemMsg.setError("Uloga mora da postolji!");
			return false;
		}
		Uloga uloga;
		try {
			uloga = Uloga.valueOf(kor.getUloga());
		}catch (Exception e) {
			problemMsg.setError("Uloga je loseg formata!");
			return false;
		}
		
		Korisnik korisnik = new Korisnik();
		korisnik.setEmail(kor.getEmail());
		if(kor.getLozinka() != null)
			korisnik.setLozinka(kor.getLozinka());
		korisnik.setIme(kor.getIme());
		korisnik.setPrezime(kor.getPrezime());
		korisnik.setOrganizacija(organizacija);
		korisnik.setUloga(uloga);
		korisnici.add(korisnik);
		
		return true;
	}
	
	public Korisnik find(String email) {
		for (Korisnik korisnik : korisnici) {
			if(korisnik.getEmail().equals(email)) {
				return korisnik;
			}
		}
		return null;
	}

	public List<Korisnik> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(List<Korisnik> korisnici) {
		this.korisnici = korisnici;
	}
	
}
