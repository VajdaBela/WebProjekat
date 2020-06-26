package lists;

import java.util.ArrayList;
import java.util.List;

import app.JsonError;
import data.Organizacija;
import dto.OrganizacijaDTO;

public class OrganizacijaList {
	private List<Organizacija> organizacije = new ArrayList<>();
	public JsonError problemMsg = new JsonError();
	
	public boolean addOrganizacija(OrganizacijaDTO org) {
		if(org.getIme() == null || org.getIme().equals(""))
		{
			problemMsg.setError("Ime mora da postoji!");;
			return false;
		}
		if(find(org.getIme()) != null) {
			problemMsg.setError("Ime nije jedinstven!");;
			return false;
		}
		
		Organizacija organizacija = new Organizacija();
		organizacija.setIme(org.getIme());
		if(org.getOpis() != null)
			organizacija.setOpis(org.getOpis());
		if(org.getLogo() != null)
			organizacija.setLogo(org.getLogo());
		
		organizacije.add(organizacija);
		
		return true;
	}

	public Organizacija find(String ime) {
		for (Organizacija org : organizacije) {
			if(org.getIme().equals(ime)) {
				return org;
			}
		}
		problemMsg.setError("Organizacija ne postolji!");
		return null;
	}
	
	public boolean changeOrganizacija(OrganizacijaDTO org, String original) {
		Organizacija organizazija = find(original);
		if(organizazija == null) {
			return false;
		}
		organizacije.remove(organizazija);
		if(find(org.getIme()) != null) {
			organizacije.add(organizazija);
			problemMsg.setError("Vec ima organizacija sa imenom!");
			return false;
		}
		
		organizazija.setIme(org.getIme());
		if(org.getOpis() == null)
			organizazija.setOpis("");
		else
			organizazija.setOpis(org.getOpis());
		if(org.getLogo() == null)
			organizazija.setLogo("");
		else
			organizazija.setLogo(org.getLogo());
		organizacije.add(organizazija);

		return true;
	}

	public List<Organizacija> getOrganizacije() {
		return organizacije;
	}

	public void setOrganizacije(List<Organizacija> organizacije) {
		this.organizacije = organizacije;
	}

}
