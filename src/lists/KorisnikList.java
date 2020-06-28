package lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.AllLists;
import app.JsonError;
import data.Korisnik;
import data.Organizacija;
import data.Korisnik.Uloga;
import dto.KorisnikDTO;

//TODO ne menjati organizaciju
public class KorisnikList {
	private HashMap<String , Korisnik> korisnici = new HashMap<>();
	public JsonError problemMsg = new JsonError();
	
	public List<Korisnik> getByOrganization(Organizacija organization) {
		if(organization == null) {
			return new ArrayList<>(korisnici.values());
		} 
		else {
			return new ArrayList<>(organization.getKorisnici().values());
		}
	}
	
	public boolean addKorisnik(KorisnikDTO kor) {
		if(!checkVAlid(kor)) {
			return false;
		}
		
		Korisnik korisnik = makeKorisnik(kor);
		korisnici.put(korisnik.getEmail(), korisnik);
		return true;
	}
	
	public boolean changeKorisnik(KorisnikDTO kor, String original) {
		Korisnik stari = find(original);
		if(stari == null) {
			return false;
		}
		korisnici.remove(original);
		if(!checkVAlid(kor)) {
			korisnici.put(stari.getEmail(), stari);
			return false;
		}
		Korisnik korisnik = makeKorisnik(kor);
		korisnici.put(korisnik.getEmail(), korisnik);
		return true;
	}
	
	public boolean deleteKorisnik(String kor) {
		Korisnik korisnik = find(kor);
		if(korisnik == null) {
			return false;
		}
		korisnici.remove(kor);
		korisnik.getOrganizacija().removeKorisnik(korisnik);
		return true;
	}
	
	public Korisnik find(String email) {
		Korisnik korisnik = korisnici.get(email);
		if(korisnik == null)
			problemMsg.setError("Korisnik ne postolji!");
		return korisnik;
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
		organizacija.addKorisnik(korisnik);
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

	public HashMap<String, Korisnik> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(HashMap<String, Korisnik> korisnici) {
		this.korisnici = korisnici;
	}

	public JsonError getProblemMsg() {
		return problemMsg;
	}

	public void setProblemMsg(JsonError problemMsg) {
		this.problemMsg = problemMsg;
	}


	
}
