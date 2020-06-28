package lists;

import java.util.HashMap;

import app.JsonError;
import data.Kategorija;

public class KategorijaList {
	private HashMap<String, Kategorija> kategorije = new HashMap<>();
	public JsonError problemMsg = new JsonError();

	public boolean addKategorija(Kategorija kat) {
		if (!checkValid(kat)) {
			return false;
		}
		kategorije.put(kat.getIme(), kat);
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
		// TODO edit
		kategorije.put(kat.getIme(), kat);
		return true;
	}

	public boolean deleteKategorija(String kategorija) {
		Kategorija kat = find(kategorija);
		if (kat == null) {
			return false;
		}
		// TODO check if used
		kategorije.remove(kategorija);
		return true;
	}

	private boolean checkValid(Kategorija kat) {
		if (kat.getIme() == null || kat.getIme().equals("")) {
			problemMsg.setError("Ime mora da postolji!");
			return false;
		}
		if (find(kat.getIme()) != null) {
			problemMsg.setError("Ime nije jedinstven!");
			return false;
		}

		if (kat.getBrJezgara() <= 0) {
			problemMsg.setError("Broj jezgara mora biti veci od nule!");
			return false;
		}

		if (kat.getRam() <= 0) {
			problemMsg.setError("Kapacitet rama u GB mora biti veci od nule!");
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
