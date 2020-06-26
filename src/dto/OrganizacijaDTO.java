package dto;

import data.Organizacija;

public class OrganizacijaDTO {
	private String ime;
	private String opis;
	private String logo;

	public OrganizacijaDTO() {
	}

	public OrganizacijaDTO(Organizacija organizacija) {
		super();
		this.ime = organizacija.getIme();
		this.opis = organizacija.getOpis();
		this.logo = organizacija.getLogo();
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
}
