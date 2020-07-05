package lists;

import java.util.HashMap;

import app.AllLists;
import app.JsonError;
import data.Kategorija;
import data.VirtualnaMasina;

public class KategorijaList {
	private HashMap<String, Kategorija> kategorije = new HashMap<>();
	public JsonError problemMsg = new JsonError();

	public boolean addKategorija(Kategorija kat) {
		if (!checkValid(kat)) {
			return false;
		}
		kategorije.put(kat.getIme(), kat);
		AllLists.saveKategorija();
		return true;
	}

	public Kategorija find(String ime) {
		Kategorija kat = kategorije.get(ime);
		if (kat == null)
			problemMsg.setError("Kategorija ne postolji!");
		return kat;
	}

	public boolean changeKategorija(Kategorija kat, String original) {
		Kategorija stari = find(original);
		if (stari == null) {
			return false;
		}
		kategorije.remove(original);
		if (!checkValid(kat)) {
			kategorije.put(stari.getIme(), stari);
			return false;
		}
		
		stari.setIme(kat.getIme());
		stari.setRam(kat.getRam());
		stari.setGpu(kat.getGpu());
		stari.setBrJezgara(kat.getBrJezgara());
		kategorije.put(stari.getIme(), stari);
		AllLists.saveKategorija();
		return true;
	}

	public boolean deleteKategorija(String kategorija) {
		Kategorija kat = find(kategorija);
		if (kat == null) {
			return false;
		}
		for(VirtualnaMasina virtualanMasina : AllLists.virtualneMasine.getVirtualneMasine().values()) {
			if(virtualanMasina.getKategorija() == kat) {
				problemMsg.setError("Kategorija is in use!");
				return false;
			}
		}
		kategorije.remove(kategorija);
		AllLists.saveKategorija();
		return true;
	}

	private boolean checkValid(Kategorija kat) {
		if (kat.getIme() == null || kat.getIme().equals("")) {
			problemMsg.setError("Ime must exist!");
			return false;
		}
		if (find(kat.getIme()) != null) {
			problemMsg.setError("Ime is not unique!");
			return false;
		}

		if (kat.getBrJezgara() <= 0) {
			problemMsg.setError("Broj jezgara must be positive!");
			return false;
		}

		if (kat.getRam() <= 0) {
			problemMsg.setError("Kapacitet rama u GB must be positive!");
			return false;
		}
		
		if (kat.getGpu() < 0) {
			problemMsg.setError("Broj GPU jezgara must not be negative!");
			return false;
		}

		return true;
	}

	public HashMap<String, Kategorija> getKategorije() {
		return kategorije;
	}

	public void setKategorije(HashMap<String, Kategorija> kategorije) {
		this.kategorije = kategorije;
	}

	public JsonError getProblemMsg() {
		return problemMsg;
	}

	public void setProblemMsg(JsonError problemMsg) {
		this.problemMsg = problemMsg;
	}
}
