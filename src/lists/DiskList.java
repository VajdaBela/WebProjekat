package lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.AllLists;
import app.JsonError;
import data.Disk;
import data.Disk.Tip;
import data.Organizacija;
import data.VirtualnaMasina;
import dto.DiskDTO;

public class DiskList {
	private HashMap<String, Disk> diskovi = new HashMap<>();
	public JsonError problemMsg = new JsonError();
	
	public List<Disk> getByOrganization(Organizacija organizacija) {
		if(organizacija == null) {
			return new ArrayList<>(diskovi.values());
		} else {
			return new ArrayList<>(organizacija.getDiskovi().values());
		}
	}

	public boolean addDisk(DiskDTO disk) {
		if (!checkValid(disk)) {
			return false;
		}

		Disk newDisk = makeDisk(disk);
		diskovi.put(newDisk.getIme(), newDisk);
		AllLists.saveDisk();
		return true;
	}

	public boolean changeDisk(DiskDTO di, String original) {
		Disk stari = find(original);
		if (stari == null) {
			return false;
		}
		diskovi.remove(original);
		if (!checkValid(di)) {
			diskovi.put(stari.getIme(), stari);
			return false;
		}

		edit(stari, di);
		diskovi.put(stari.getIme(), stari);
		AllLists.saveDisk();
		return true;
	}

	public boolean deleteDisk(String ime) {
		Disk disk = find(ime);
		if (disk == null) {
			return false;
		}
		diskovi.remove(disk.getIme());
		disk.getOrganizacija().removeDisk(disk);
		disk.getVirtualanaMasina().removeDisk(disk);
		AllLists.saveDisk();
		return true;
	}

	public Disk find(String ime) {
		Disk disk = diskovi.get(ime);
		if (disk == null)
			problemMsg.setError("Disk not found!");
		return disk;
	}

	private boolean checkValid(DiskDTO disk) {
		if (disk.getIme() == null || disk.getIme().equals("")) {
			problemMsg.setError("Ime mora da postolji!");
			return false;
		}
		if (find(disk.getIme()) != null) {
			problemMsg.setError("Ime nije jedinstven!");
			return false;
		}

		if (disk.getTip() == null || disk.getTip().equals("")) {
			problemMsg.setError("Tip mora da postolji!");
			return false;
		}
		try {
			Tip.valueOf(disk.getTip());
		} catch (Exception e) {
			problemMsg.setError("Tip je loseg formata!");
			return false;
		}

		if (disk.getKapacitet() <= 0) {
			problemMsg.setError("Kapacitet diska u GB mora biti vece od nule!");
			return false;
		}

		if (disk.getVirtualnaMasina() == null) {
			problemMsg.setError("Virtualan masina mora da postolji!");
			return false;
		}
		VirtualnaMasina virtualnaMasina = AllLists.virtualneMasine.find(disk.getVirtualnaMasina());
		if (virtualnaMasina == null) {
			problemMsg.setError("Ne postolji virtualna masina!");
			return false;
		}

		return true;
	}

	private Disk makeDisk(DiskDTO disk) {
		Disk madeDisk = new Disk();

		madeDisk.setIme(disk.getIme());
		madeDisk.setTip(Tip.valueOf(disk.getTip()));
		madeDisk.setKapacitet(disk.getKapacitet());
		VirtualnaMasina virtualnaMasina = AllLists.virtualneMasine.find(disk.getVirtualnaMasina());
		madeDisk.setVirtualanaMasina(virtualnaMasina);
		virtualnaMasina.addDisk(madeDisk);
		madeDisk.setOrganizacija(madeDisk.getVirtualanaMasina().getOrganizacija());
		madeDisk.getOrganizacija().addDisk(madeDisk);
		return madeDisk;
	}
	
	private void edit(Disk disk, DiskDTO di) {
		disk.setIme(di.getIme());
		disk.setTip(Tip.valueOf(di.getTip()));
		disk.setKapacitet(di.getKapacitet());
		VirtualnaMasina vm = AllLists.virtualneMasine.find(di.getVirtualnaMasina());
		disk.getVirtualanaMasina().removeDisk(disk);
		disk.setVirtualanaMasina(vm);
		disk.getVirtualanaMasina().addDisk(disk);
		disk.getOrganizacija().removeDisk(disk);
		disk.setOrganizacija(disk.getVirtualanaMasina().getOrganizacija());
		disk.getOrganizacija().addDisk(disk);
	}

	public HashMap<String, Disk> getDiskovi() {
		return diskovi;
	}

	public void setDiskovi(HashMap<String, Disk> diskovi) {
		this.diskovi = diskovi;
	}

}
