package data;

import java.util.ArrayList;
import java.util.List;

public class Organizacija {
	private String ime;
	private String opis;
	private String Logo;
	private List<String> korisnici;
	private List<String> masine;
	private List<String> diskovi;

	public Organizacija() {
		this.ime = "";
		this.opis = "";
		this.Logo = "defaultLogo.img";
		this.korisnici = new ArrayList<>();
		this.masine = new ArrayList<>();
		this.diskovi = new ArrayList<>();
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
		return Logo;
	}

	public void setLogo(String logo) {
		Logo = logo;
	}

	public List<String> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(List<String> korisnici) {
		this.korisnici = korisnici;
	}

	public List<String> getMasine() {
		return masine;
	}

	public void setMasine(List<String> masine) {
		this.masine = masine;
	}

	public List<String> getDiskovi() {
		return diskovi;
	}

	public void setDiskovi(List<String> diskovi) {
		this.diskovi = diskovi;
	}

}
