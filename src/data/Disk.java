package data;

public class Disk {
	private String ime;
	private Organizacija organizacija;
	private Tip tip;
	private int kapacitet;
	private VirtualnaMasina virtualanaMasina;

	public Disk() {
		this.ime = "";
		this.organizacija = null;
		this.tip = Tip.HDD;
		this.kapacitet = 0;
		this.virtualanaMasina = null;
	}

	public Disk(String ime, Tip tip, int kapacitet, Organizacija organizacija, VirtualnaMasina virtualnaMasina) {
		this();
		this.ime = ime;
		this.tip = tip;
		this.kapacitet = kapacitet;
		this.organizacija = organizacija;
		this.virtualanaMasina = virtualnaMasina;
	}
	
	public boolean isMyUser(Korisnik kor) {
		for (Korisnik korisnik : organizacija.getKorisnici().values()) {
			if(kor == korisnik) {
				return true;
			}
		}
		return false;
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

	public Tip getTip() {
		return tip;
	}

	public void setTip(Tip tip) {
		this.tip = tip;
	}

	public int getKapacitet() {
		return kapacitet;
	}

	public void setKapacitet(int kapacitet) {
		this.kapacitet = kapacitet;
	}

	public VirtualnaMasina getVirtualanaMasina() {
		return virtualanaMasina;
	}

	public void setVirtualanaMasina(VirtualnaMasina virtualanaMasina) {
		this.virtualanaMasina = virtualanaMasina;
	}

	public enum Tip {
		SSD, HDD
	}
}
