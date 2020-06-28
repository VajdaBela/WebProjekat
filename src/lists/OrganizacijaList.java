package lists;

import java.util.HashMap;

import app.JsonError;
import data.Organizacija;
import dto.OrganizacijaDTO;

public class OrganizacijaList {
	private HashMap<String, Organizacija> organizacije = new HashMap<>();
	public JsonError problemMsg = new JsonError();

	public boolean addOrganizacija(OrganizacijaDTO org) {
		if (!checkValid(org)) {
			return false;
		}
		Organizacija organizacija = makeOrganizacija(org);
		organizacije.put(organizacija.getIme(), organizacija);
		return true;
	}

	public Organizacija find(String ime) {
		Organizacija organizacija = organizacije.get(ime);
		if (organizacija == null)
			problemMsg.setError("Organizacija ne postolji!");
		return organizacija;
	}

	public boolean changeOrganizacija(OrganizacijaDTO org, String original) {
		// TODO edit instead of make
		Organizacija stari = find(original);
		if (stari == null) {
			return false;
		}
		organizacije.remove(original);
		if (!checkValid(org)) {
			organizacije.put(stari.getIme(), stari);
			return false;
		}
		Organizacija organizacija = makeOrganizacija(org);
		organizacije.put(organizacija.getIme(), organizacija);
		return true;
	}

	private boolean checkValid(OrganizacijaDTO org) {
		if (org.getIme() == null || org.getIme().equals("")) {
			problemMsg.setError("Ime mora da postolji!");
			return false;
		}
		if (find(org.getIme()) != null) {
			problemMsg.setError("Ime nije jedinstven!");
			return false;
		}
		return true;
	}

	private Organizacija makeOrganizacija(OrganizacijaDTO dto) {
		Organizacija org = new Organizacija();
		if (dto.getIme() != null)
			org.setIme(dto.getIme());
		if (dto.getOpis() != null)
			org.setOpis(dto.getOpis());
		if (dto.getLogo() != null)
			org.setLogo(dto.getLogo());
		return org;
	}

	public HashMap<String, Organizacija> getOrganizacije() {
		return organizacije;
	}

	public void setOrganizacije(HashMap<String, Organizacija> organizacije) {
		this.organizacije = organizacije;
	}

}
