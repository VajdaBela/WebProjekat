package data;

import java.util.ArrayList;
import java.util.List;

public class Organizacija {
	private String ime;
	private String opis;
	private String logo;
	private List<Korisnik> korisnici;
	private List<VirtualnaMasina> masine;
	private List<Disk> diskovi;

	public Organizacija() {
		this.ime = "";
		this.opis = "";
		this.logo = "defaultLogo.img";
		this.korisnici = new ArrayList<>();
		this.masine = new ArrayList<>();
		this.diskovi = new ArrayList<>();
	}

	public Organizacija(String ime, String opis) {
		this();
		this.ime = ime;
		this.opis = opis;
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

	public List<Korisnik> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(List<Korisnik> korisnici) {
		this.korisnici = korisnici;
	}

	public List<VirtualnaMasina> getMasine() {
		return masine;
	}

	public void setMasine(List<VirtualnaMasina> masine) {
		this.masine = masine;
	}

	public List<Disk> getDiskovi() {
		return diskovi;
	}

	public void setDiskovi(List<Disk> diskovi) {
		this.diskovi = diskovi;
	}

}
