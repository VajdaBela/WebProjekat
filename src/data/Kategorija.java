package data;

public class Kategorija {
	private String ime;
	private int brJezgara;
	private int ram;
	private int gpu;

	public Kategorija() {
		this.ime = "";
	}

	public Kategorija(String ime, int brJezgara, int ram, int gpu) {
		super();
		this.ime = ime;
		this.brJezgara = brJezgara;
		this.ram = ram;
		this.gpu = gpu;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public int getBrJezgara() {
		return brJezgara;
	}

	public void setBrJezgara(int brJezgara) {
		this.brJezgara = brJezgara;
	}

	public int getRam() {
		return ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
	}

	public int getGpu() {
		return gpu;
	}

	public void setGpu(int gpu) {
		this.gpu = gpu;
	}

}
