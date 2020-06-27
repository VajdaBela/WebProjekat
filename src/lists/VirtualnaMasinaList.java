package lists;

import java.util.ArrayList;
import java.util.List;

import app.AllLists;
import app.JsonError;
import data.Kategorija;
import data.Organizacija;
import data.VirtualnaMasina;
import dto.VirtualnaMasinaDTO;

public class VirtualnaMasinaList {
	private List<VirtualnaMasina> virtualneMasine = new ArrayList<>();
	public JsonError problemMsg = new JsonError();

	public List<VirtualnaMasina> getByOrganization(Organizacija organizacija) {
		if(organizacija == null) {
			return virtualneMasine;
		}
		List<VirtualnaMasina> sameOrgVirtualneMasine = new ArrayList<>();
		for (VirtualnaMasina virtualnaMasina : virtualneMasine) {
			if(virtualnaMasina.getOrganizacija() == organizacija) {
				sameOrgVirtualneMasine.add(virtualnaMasina);
			}
		}
		return sameOrgVirtualneMasine;
	}
	
	public boolean addVirtualnaMasina(VirtualnaMasinaDTO virm) {
		if(!checkValid(virm)) {
			return false;
		}
		virtualneMasine.add(makeVirtualnaMasina(virm));
		return true;
	}
	
	public boolean changeVirtualnaMasina(VirtualnaMasinaDTO virm, String original) {
		VirtualnaMasina stari = find(original);
		if(stari == null) {
			return false;
		}
		virtualneMasine.remove(stari);
		if(!checkValid(virm)) {
			virtualneMasine.add(stari);
			return false;
		}
		virtualneMasine.add(makeVirtualnaMasina(virm));
		return true;
	}
	
	public boolean deleteVirtualnaMasina(String ime) {
		VirtualnaMasina virtualnaMasina = find(ime);
		if(virtualnaMasina == null) {
			return false;
		}
		virtualneMasine.remove(virtualnaMasina);
		virtualnaMasina.getOrganizacija().getMasine().remove(virtualnaMasina);
		return true;
	}
	
	//TODO change makes references vanish
	private VirtualnaMasina makeVirtualnaMasina(VirtualnaMasinaDTO virm) {
		VirtualnaMasina virtualnaMasina = new VirtualnaMasina();
		
		virtualnaMasina.setIme(virm.getIme());
		Organizacija organizacija = AllLists.organizacije.find(virm.getOrganizacija());
		virtualnaMasina.setOrganizacija(organizacija);
		organizacija.getMasine().add(virtualnaMasina);
		Kategorija kategorija = AllLists.kategorije.find(virm.getKategorija());
		virtualnaMasina.setKategorija(kategorija);
		
		return virtualnaMasina;
	}
	
	public boolean checkValid(VirtualnaMasinaDTO virm) {
		if(virm.getIme() == null || virm.getIme().equals("")) {
			problemMsg.setError("Ime mora da postolji!");
			return false;
		}
		if(find(virm.getIme()) != null) {
			problemMsg.setError("Ime nije jedinstven!");
			return false;
		}
		
		if(virm.getOrganizacija() == null) {
			problemMsg.setError("Organizacija mora da postolji!");
			return false;
		}
		Organizacija organizacija = AllLists.organizacije.find(virm.getOrganizacija());
		if(organizacija == null) {
			problemMsg.setError("Ne postolji organizacija!");
			return false;
		}
		
		if(virm.getKategorija() == null) {
			problemMsg.setError("Kategorija mora da postolji!");
			return false;
		}
		Kategorija kategorija = AllLists.kategorije.find(virm.getKategorija());
		if(kategorija == null) {
			problemMsg.setError("Ne postolji kategorija!");
			return false;
		}
		
		return true;
	}
	
	public VirtualnaMasina find(String ime) {
		for (VirtualnaMasina virtualnaMasina : virtualneMasine) {
			if(virtualnaMasina.getIme().equals(ime)) {
				return virtualnaMasina;
			}
		}
		problemMsg.setError("Virtualna masina ne postolji!");
		return null;
	}
	
	public List<VirtualnaMasina> getVirtualneMasine() {
		return virtualneMasine;
	}

	public void setVirtualneMasine(List<VirtualnaMasina> virtualneMasine) {
		this.virtualneMasine = virtualneMasine;
	}

}
