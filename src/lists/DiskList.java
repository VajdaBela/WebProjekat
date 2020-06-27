package lists;

import java.util.ArrayList;
import java.util.List;

import app.AllLists;
import app.JsonError;
import data.Disk;
import data.Disk.Tip;
import data.Organizacija;
import data.VirtualnaMasina;
import dto.DiskDTO;

public class DiskList {
	private List<Disk> diskovi = new ArrayList<>();
	public JsonError problemMsg = new JsonError();
	
	public boolean addDisk(DiskDTO disk) {
		if(!checkValid(disk)) {
			return false;
		}
		
		diskovi.add(makeDisk(disk));
		return true;
	}
	
	public boolean changeDisk(DiskDTO di, String original) {
		Disk stari = find(original);
		if(stari == null) {
			return false;
		}
		diskovi.remove(stari);
		if(!checkValid(di)) {
			diskovi.add(stari);
			return false;
		}
		diskovi.add(makeDisk(di));
		return true;
	}
	
	public boolean deleteDisk(String ime) {
		Disk disk = find(ime);
		if(disk == null) {
			return false;
		}
		diskovi.remove(disk);
		disk.getOrganizacija().getDiskovi().remove(disk);
		disk.getVirtualanaMasina().getDiskovi().remove(disk);
		return true;
	}
	
	public Disk find(String ime) {
		for (Disk disk : diskovi) {
			if(disk.getIme().equals(ime)) {
				return disk;
			}
		}
		return null;
	}
	
	private boolean checkValid(DiskDTO disk) {
		if(disk.getIme() == null || disk.getIme().equals("")) {
			problemMsg.setError("Ime mora da postolji!");
			return false;
		}
		if(find(disk.getIme()) != null) {
			problemMsg.setError("Ime nije jedinstven!");
			return false;
		}
		
		if(disk.getOrganizacija() == null) {
			problemMsg.setError("Organizacija mora da postolji!");
			return false;
		}
		Organizacija organizacija = AllLists.organizacije.find(disk.getOrganizacija());
		if(organizacija == null) {
			problemMsg.setError("Ne postolji organizacija!");
			return false;
		}
		
		if(disk.getTip() == null || disk.getTip().equals("")) {
			problemMsg.setError("Tip mora da postolji!");
			return false;
		}
		try {
			Tip.valueOf(disk.getTip());
		}catch (Exception e) {
			problemMsg.setError("Tip je loseg formata!");
			return false;
		}
		
		if(disk.getKapacitet() <= 0) {
			problemMsg.setError("Kapacitet diska u GB mora biti vece od nule!");
			return false;
		}
		
		if(disk.getVirtualnaMasina() == null) {
			problemMsg.setError("Virtualan masina mora da postolji!");
			return false;
		}
		VirtualnaMasina virtualnaMasina = AllLists.virtualneMasine.find(disk.getVirtualnaMasina());
		if(virtualnaMasina == null) {
			problemMsg.setError("Ne postolji virtualna masina!");
			return false;
		}
		
		return true;
	}
	
	private Disk makeDisk(DiskDTO disk) {
		Disk madeDisk = new Disk();
		
		madeDisk.setIme(disk.getIme());
		Organizacija organizacija = AllLists.organizacije.find(disk.getOrganizacija());
		madeDisk.setOrganizacija(organizacija);
		organizacija.getDiskovi().add(madeDisk);
		madeDisk.setTip(Tip.valueOf(disk.getTip()));
		madeDisk.setKapacitet(disk.getKapacitet());
		VirtualnaMasina virtualnaMasina = AllLists.virtualneMasine.find(disk.getVirtualnaMasina());
		madeDisk.setVirtualanaMasina(virtualnaMasina);
		virtualnaMasina.getDiskovi().add(madeDisk);
		
		return madeDisk;
	}

	public List<Disk> getDiskovi() {
		return diskovi;
	}

	public void setDiskovi(List<Disk> diskovi) {
		this.diskovi = diskovi;
	}

}
