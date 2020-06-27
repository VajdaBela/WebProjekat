package lists;

import java.util.ArrayList;
import java.util.List;

import app.JsonError;
import data.Kategorija;

public class KategorijaList {
	private List<Kategorija> kategorije = new ArrayList<>();
	public JsonError problemMsg = new JsonError();
	
	public boolean addKategorija(Kategorija kat) {
		if(!checkValid(kat)) {
			return false;
		}
		kategorije.add(kat);
		return true;
	}
	
	public Kategorija find(String ime) {
		for (Kategorija kategorija : kategorije) {
			if(kategorija.getIme().equals(ime)) {
				return kategorija;
			}
		}
		problemMsg.setError("Kategorija ne postolji!");
		return null;
	}
	
	public boolean changeKategorija(Kategorija kat, String original) {
		Kategorija stari = find(original);
		if(stari == null) {
			return false;
		}
		kategorije.remove(stari);
		if(!checkValid(kat)) {
			kategorije.add(stari);
			return false;
		}
		kategorije.add(kat);
		return true;
	}
	
	public boolean deleteKategorija(String kategorija) {
		Kategorija kat = find(kategorija);
		if(kat == null) {
			return false;
		}
		//TODO check if used
		kategorije.remove(kat);
		return true;
	}
	
	private boolean checkValid(Kategorija kat) {
		if(kat.getIme() == null || kat.getIme().equals("")) {
			problemMsg.setError("Ime mora da postolji!");
			return false;
		}
		if(find(kat.getIme()) != null) {
			problemMsg.setError("Ime nije jedinstven!");
			return false;
		}
		
		if(kat.getBrJezgara() <= 0) {
			problemMsg.setError("Broj jezgara mora biti veci od nule!");
			return false;
		}
		
		if(kat.getRam() <= 0) {
			problemMsg.setError("Kapacitet rama u GB mora biti veci od nule!");
			return false;
		}
		
		return true;
	}

	public List<Kategorija> getKategorije() {
		return kategorije;
	}

	public void setKategorije(List<Kategorija> kategorije) {
		this.kategorije = kategorije;
	}

	public JsonError getProblemMsg() {
		return problemMsg;
	}

	public void setProblemMsg(JsonError problemMsg) {
		this.problemMsg = problemMsg;
	}
}
