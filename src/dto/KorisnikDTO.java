package dto;

import data.Korisnik;

public class KorisnikDTO {
	private String email;
	private String ime;
	private String prezime;
	private String organizacija;

	public KorisnikDTO() {
		super();
	}

	public KorisnikDTO(Korisnik korisnik) {
		super();
		this.email = korisnik.getEmail();
		this.ime = korisnik.getIme();
		this.prezime = korisnik.getPrezime();
		this.organizacija = korisnik.getOrganizacija() == null ? "" : korisnik.getOrganizacija().getIme();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getOrganizacija() {
		return organizacija;
	}

	public void setOrganizacija(String organizacija) {
		this.organizacija = organizacija;
	}

}
