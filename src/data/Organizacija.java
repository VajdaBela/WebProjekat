package data;

import java.util.List;

public class Organizacija {
	private String ime;
	private String opis;
	private String Logo;
	private List<Korisnik> korisnici;
	private List<VirtualnaMasina> masine;
	private List<Disk> diskovi;

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
