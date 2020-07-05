package lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.AllLists;
import app.JsonError;
import data.Kategorija;
import data.Organizacija;
import data.VirtualnaMasina;
import dto.VirtualnaMasinaDTO;

public class VirtualnaMasinaList {
	private HashMap<String, VirtualnaMasina> virtualneMasine = new HashMap<>();
	public JsonError problemMsg = new JsonError();

	public List<VirtualnaMasina> getByOrganization(Organizacija organizacija) {
		if (organizacija == null) {
			return new ArrayList<>(virtualneMasine.values());
		} else {
			return new ArrayList<>(organizacija.getMasine().values());
		}
	}

	public boolean addVirtualnaMasina(VirtualnaMasinaDTO virm) {
		if (!checkValid(virm, true)) {
			return false;
		}
		VirtualnaMasina masina = makeVirtualnaMasina(virm);
		virtualneMasine.put(masina.getIme(), masina);
		return true;
	}

	public boolean changeVirtualnaMasina(VirtualnaMasinaDTO virm, String original) {
		VirtualnaMasina stari = find(original);
		if (stari == null) {
			return false;
		}
		virtualneMasine.remove(original);
		if (!checkValid(virm, false)) {
			virtualneMasine.put(stari.getIme(), stari);
			return false;
		}
		stari.setKategorija(AllLists.kategorije.getKategorije().get(virm.getKategorija()));
		stari.setIme(virm.getIme());
		virtualneMasine.put(stari.getIme(), stari);
		return true;
	}

	public boolean deleteVirtualnaMasina(String ime) {
		VirtualnaMasina virtualnaMasina = find(ime);
		if (virtualnaMasina == null) {
			return false;
		}
		virtualneMasine.remove(ime);
		virtualnaMasina.getOrganizacija().removeMasina(virtualnaMasina);
		return true;
	}

	// TODO change makes references vanish
	private VirtualnaMasina makeVirtualnaMasina(VirtualnaMasinaDTO virm) {
		VirtualnaMasina virtualnaMasina = new VirtualnaMasina();

		virtualnaMasina.setIme(virm.getIme());
		Organizacija organizacija = AllLists.organizacije.find(virm.getOrganizacija());
		virtualnaMasina.setOrganizacija(organizacija);
		organizacija.addMasina(virtualnaMasina);
		Kategorija kategorija = AllLists.kategorije.find(virm.getKategorija());
		virtualnaMasina.setKategorija(kategorija);

		return virtualnaMasina;
	}

	public boolean checkValid(VirtualnaMasinaDTO virm, boolean novi) {
		if (virm.getIme() == null || virm.getIme().equals("")) {
			problemMsg.setError("Ime mora da postolji!");
			return false;
		}
		if (find(virm.getIme()) != null) {
			problemMsg.setError("Ime nije jedinstven!");
			return false;
		}

		if (novi && virm.getOrganizacija() == null) {
			problemMsg.setError("Organizacija mora da postolji!");
			return false;
		}
		Organizacija organizacija = AllLists.organizacije.find(virm.getOrganizacija());
		if (novi && organizacija == null) {
			problemMsg.setError("Ne postolji organizacija!");
			return false;
		}

		if (virm.getKategorija() == null) {
			problemMsg.setError("Kategorija mora da postolji!");
			return false;
		}
		Kategorija kategorija = AllLists.kategorije.find(virm.getKategorija());
		if (kategorija == null) {
			problemMsg.setError("Ne postolji kategorija!");
			return false;
		}

		return true;
	}

	public VirtualnaMasina find(String ime) {
		VirtualnaMasina masina = virtualneMasine.get(ime);
		if (masina == null)
			problemMsg.setError("Virtualna masina ne postolji!");
		return masina;
	}

	public HashMap<String, VirtualnaMasina> getVirtualneMasine() {
		return virtualneMasine;
	}

	public void setVirtualneMasine(HashMap<String, VirtualnaMasina> virtualneMasine) {
		this.virtualneMasine = virtualneMasine;
	}

	public JsonError getProblemMsg() {
		return problemMsg;
	}

	public void setProblemMsg(JsonError problemMsg) {
		this.problemMsg = problemMsg;
	}

}
