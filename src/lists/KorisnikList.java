package lists;

import java.util.ArrayList;
import java.util.List;

import app.AllLists;
import app.JsonError;
import data.Korisnik;
import data.Organizacija;
import data.Korisnik.Uloga;
import dto.KorisnikDTO;

//TODO ne menjati organizaciju
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
		if(!checkVAlid(kor)) {
			return false;
		}
		
		korisnici.add(makeKorisnik(kor));
		return true;
	}
	
	public boolean changeKorisnik(KorisnikDTO kor, String original) {
		Korisnik stari = find(original);
		if(stari == null) {
			return false;
		}
		korisnici.remove(stari);
		if(!checkVAlid(kor)) {
			korisnici.add(stari);
			return false;
		}
		korisnici.add(makeKorisnik(kor));
		return true;
	}
	
	public boolean deleteKorisnik(String kor) {
		Korisnik korisnik = find(kor);
		if(korisnik == null) {
			return false;
		}
		korisnici.remove(korisnik);
		korisnik.getOrganizacija().getKorisnici().remove(korisnik);
		return true;
	}
	
	public Korisnik find(String email) {
		for (Korisnik korisnik : korisnici) {
			if(korisnik.getEmail().equals(email)) {
				return korisnik;
			}
		}
		problemMsg.setError("Korisnik ne postolji!");
		return null;
	}
	
	private Korisnik makeKorisnik(KorisnikDTO kor) {
		Korisnik korisnik = new Korisnik();
		
		korisnik.setEmail(kor.getEmail());
		if(kor.getLozinka() != null)
			korisnik.setLozinka(kor.getLozinka());
		korisnik.setIme(kor.getIme());
		korisnik.setPrezime(kor.getPrezime());
		Organizacija organizacija = AllLists.organizacije.find(kor.getOrganizacija());
		korisnik.setOrganizacija(organizacija);
		organizacija.getKorisnici().add(korisnik);
		korisnik.setUloga(Uloga.valueOf(kor.getUloga()));
		
		return korisnik;
	}
	
	private boolean checkVAlid(KorisnikDTO kor) {
		if(kor.getEmail() == null || kor.getEmail().equals("")) {
			problemMsg.setError("Email mora da postolji!");
			return false;
		}
		if(find(kor.getEmail()) != null) {
			problemMsg.setError("Email nije jedinstven!");
			return false;
		}
		
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
			problemMsg.setError("Ne postolji organizacija!");
			return false;
		}
		
		if(kor.getUloga() == null || kor.getUloga().equals("")) {
			problemMsg.setError("Uloga mora da postolji!");
			return false;
		}
		try {
			Uloga.valueOf(kor.getUloga());
		}catch (Exception e) {
			problemMsg.setError("Uloga je loseg formata!");
			return false;
		}
		
		return true;
	}

	public List<Korisnik> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(List<Korisnik> korisnici) {
		this.korisnici = korisnici;
	}
	
}
