package dto;

import data.VirtualnaMasina;

public class VirtualnaMasinaDTO {
	private String ime;
	private String organizacija;
	private String kategorija;

	public VirtualnaMasinaDTO() {
	}
	
	public VirtualnaMasinaDTO(VirtualnaMasina virtualnaMasina) {
		this.ime = virtualnaMasina.getIme();
		this.organizacija = virtualnaMasina.getOrganizacija().getIme();
		this.kategorija = virtualnaMasina.getKategorija().getIme();
	}
	
	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getOrganizacija() {
		return organizacija;
	}

	public void setOrganizacija(String organizacija) {
		this.organizacija = organizacija;
	}

	public String getKategorija() {
		return kategorija;
	}

	public void setKategorija(String kategorija) {
		this.kategorija = kategorija;
	}

}
