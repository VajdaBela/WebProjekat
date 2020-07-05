package data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class VirtualnaMasina {
	private String ime;
	private Organizacija organizacija;
	private Kategorija kategorija;
	private boolean aktivan;
	private HashMap<String, Disk> diskovi;
	private List<Date> aktivnosti;

	public VirtualnaMasina() {
		this.ime = "";
		this.organizacija = null;
		this.kategorija = null;
		this.diskovi = new HashMap<>();
		this.aktivnosti = new ArrayList<Date>();
	}

	public VirtualnaMasina(String ime, Organizacija organizacija, Kategorija kategorija) {
		this();
		this.ime = ime;
		this.organizacija = organizacija;
		this.kategorija = kategorija;
	}

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

	public Kategorija getKategorija() {
		return kategorija;
	}

	public void setKategorija(Kategorija kategorija) {
		this.kategorija = kategorija;
	}

	public HashMap<String, Disk> getDiskovi() {
		return diskovi;
	}

	public void setDiskovi(HashMap<String, Disk> diskovi) {
		this.diskovi = diskovi;
	}

	public List<Date> getAktivnosti() {
		return aktivnosti;
	}

	public void setAktivnosti(List<Date> aktivnosti) {
		this.aktivnosti = aktivnosti;
	}
	
	public void addDisk(Disk disk) {
		this.diskovi.put(disk.getIme(), disk);
	}
	
	public void removeDisk(Disk disk) {
		this.diskovi.remove(disk.getIme());
	}

	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}

}
