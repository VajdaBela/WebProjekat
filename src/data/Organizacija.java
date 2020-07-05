package data;

import java.util.HashMap;

public class Organizacija {
	private String ime;
	private String opis;
	private String logo;
	private HashMap<String, Korisnik> korisnici;
	private HashMap<String, VirtualnaMasina> masine;
	private HashMap<String, Disk> diskovi;

	public Organizacija() {
		this.ime = "";
		this.opis = "";
		this.logo = "defaultLogo.jpg";
		this.korisnici = new HashMap<>();
		this.masine = new HashMap<>();
		this.diskovi = new HashMap<>();
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

	public HashMap<String, Korisnik> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(HashMap<String, Korisnik> korisnici) {
		this.korisnici = korisnici;
	}

	public HashMap<String, VirtualnaMasina> getMasine() {
		return masine;
	}

	public void setMasine(HashMap<String, VirtualnaMasina> masine) {
		this.masine = masine;
	}

	public HashMap<String, Disk> getDiskovi() {
		return diskovi;
	}

	public void setDiskovi(HashMap<String, Disk> diskovi) {
		this.diskovi = diskovi;
	}
	
	public void addKorisnik(Korisnik korisnik) {
		this.korisnici.put(korisnik.getEmail(), korisnik);
	}
	
	public void removeKorisnik(Korisnik korisnik) {
		this.korisnici.remove(korisnik.getEmail());
	}
	
	public void addMasina(VirtualnaMasina masina) {
		this.masine.put(masina.getIme(), masina);
	}
	
	public void removeMasina(VirtualnaMasina masina) {
		this.masine.remove(masina.getIme());
	}
	
	public void addDisk(Disk disk) {
		this.diskovi.put(disk.getIme(), disk);
	}
	
	public void removeDisk(Disk disk) {
		this.diskovi.remove(disk.getIme());
	}

}
