package data;

import java.util.Date;
import java.util.List;

public class VirtualnaMasina {
	private String ime;
	private Organizacija organizacija;
	private Kategoria kategorija;
	private List<Disk> diskovi;
	private List<Date> aktivnosti;

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public Organizacija getOrganizacija() {
		return organizacija;
	}

	public void setOrganizacija(Organizacija organizacija) {
		this.organizacija = organizacija;
	}

	public Kategoria getKategorija() {
		return kategorija;
	}

	public void setKategorija(Kategoria kategorija) {
		this.kategorija = kategorija;
	}

	public List<Disk> getDiskovi() {
		return diskovi;
	}

	public void setDiskovi(List<Disk> diskovi) {
		this.diskovi = diskovi;
	}

	public List<Date> getAktivnosti() {
		return aktivnosti;
	}

	public void setAktivnosti(List<Date> aktivnosti) {
		this.aktivnosti = aktivnosti;
	}

}
