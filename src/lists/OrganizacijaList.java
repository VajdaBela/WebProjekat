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
		if(!checkValid(org)) {
			return false;
		}
		organizacije.add(makeOrganizacija(org));
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
		
		Organizacija stari = find(original);
		if(stari == null) {
			return false;
		}
		organizacije.remove(stari);
		if(!checkValid(org)) {
			organizacije.add(stari);
			return false;
		}
		organizacije.add(makeOrganizacija(org));
		return true;
	}
	
	private boolean checkValid(OrganizacijaDTO org) {
		if(org.getIme() == null || org.getIme().equals("")){
			problemMsg.setError("Ime mora da postolji!");
			return false;
		}
		if(find(org.getIme()) != null) {
			problemMsg.setError("Ime nije jedinstven!");
			return false;
		}
		return true;
	}
	
	private Organizacija makeOrganizacija(OrganizacijaDTO dto) {
		Organizacija org = new Organizacija();
		if(dto.getIme() != null)
			org.setIme(dto.getIme());
		if(dto.getOpis() != null)
			org.setOpis(dto.getOpis());
		if(dto.getLogo() != null)
			org.setLogo(dto.getLogo());
		return org;
	}

	public List<Organizacija> getOrganizacije() {
		return organizacije;
	}

	public void setOrganizacije(List<Organizacija> organizacije) {
		this.organizacije = organizacije;
	}

}
