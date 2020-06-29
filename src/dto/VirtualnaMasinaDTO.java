package dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import data.VirtualnaMasina;

public class VirtualnaMasinaDTO {
	private String ime;
	private String organizacija;
	private String kategorija;
	private List<Date> aktivnosti;

	public VirtualnaMasinaDTO() {
		this.aktivnosti = new ArrayList<>();
	}
	
	public VirtualnaMasinaDTO(VirtualnaMasina virtualnaMasina) {
		this();
		this.ime = virtualnaMasina.getIme();
		this.organizacija = virtualnaMasina.getOrganizacija().getIme();
		this.kategorija = virtualnaMasina.getKategorija().getIme();
		this.aktivnosti = virtualnaMasina.getAktivnosti();
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

	public List<Date> getAktivnosti() {
		return aktivnosti;
	}

	public void setAktivnosti(List<Date> aktivnosti) {
		this.aktivnosti = aktivnosti;
	}

}
