package data;

public class Disk {
	private String ime;
	private Organizacija organizacija;
	private Tip tip;
	private int kapacitet;
	private VirtualnaMasina virtualanaMasina;

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
