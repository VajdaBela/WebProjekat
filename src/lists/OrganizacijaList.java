package lists;

import java.util.ArrayList;
import java.util.List;

import app.JsonError;
import data.Organizacija;

public class OrganizacijaList {
	private List<Organizacija> organizacije;
	public JsonError problemMsg = new JsonError();
	public Organizacija found;

	public OrganizacijaList() {
		organizacije = new ArrayList<>();
	}
	
	public boolean addOrganizacija(Organizacija org) {
		if(org.getIme().equals(""))
		{
			problemMsg.setError("Ime mora da postoji!");;
			return false;
		}
		if(!chechkImeUnique(org.getIme())) {
			problemMsg.setError("Ime nije jedinstven!");;
			return false;
		}
		
		org.setKorisnici(new ArrayList<String>());
		org.setMasine(new ArrayList<String>());
		org.setDiskovi(new ArrayList<String>());
		
		organizacije.add(org);
		
		return true;
	}
	
	private boolean chechkImeUnique(String ime) {
		for (Organizacija org : organizacije) {
			if(ime.equals(org.getIme()))
				return false;
		}
		return true;
	}

	public boolean find(String ime) {
		for (Organizacija org : organizacije) {
			if(ime.equals(org.getIme())) {
				found = org;
				return true;
			}
		}
		problemMsg.setError("Organizacija ne postolji!");
		return false;
	}
	
	public boolean changeOrganizacija(Organizacija org, String original) {
		if(!find(original)) {
			return false;
		}
		Organizacija stari = found;
		organizacije.remove(found);
		if(find(org.getIme())) {
			organizacije.add(stari);
			problemMsg.setError("Vec ima organizacija sa imenom!");
			return false;
		}
		
		stari.setIme(org.getIme());
		stari.setOpis(org.getOpis());
		stari.setLogo(org.getLogo());
		organizacije.add(stari);

		return true;
	}

	public List<Organizacija> getOrganizacije() {
		return organizacije;
	}

	public void setOrganizacije(List<Organizacija> organizacije) {
		this.organizacije = organizacije;
	}

}
