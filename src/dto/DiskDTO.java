package dto;

import data.Disk;

public class DiskDTO {
	private String ime;
	private String organizacija;
	private String tip;
	private int kapacitet;
	private String virtualnaMasina;

	public DiskDTO() {
	}

	public DiskDTO(Disk disk) {
		super();
		this.ime = disk.getIme();
		this.organizacija = disk.getOrganizacija().getIme();
		this.tip = disk.getTip().toString();
		this.kapacitet = disk.getKapacitet();
		this.virtualnaMasina = disk.getVirtualanaMasina().getIme();
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

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public int getKapacitet() {
		return kapacitet;
	}

	public void setKapacitet(int kapacitet) {
		this.kapacitet = kapacitet;
	}

	public String getVirtualnaMasina() {
		return virtualnaMasina;
	}

	public void setVirtualnaMasina(String virtualnaMasina) {
		this.virtualnaMasina = virtualnaMasina;
	}

}
