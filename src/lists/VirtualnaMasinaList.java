package lists;

import java.util.ArrayList;
import java.util.List;

import app.JsonError;
import data.VirtualnaMasina;

public class VirtualnaMasinaList {
	private List<VirtualnaMasina> virtualneMasine = new ArrayList<>();
	public JsonError problemMsg = new JsonError();

	public VirtualnaMasina find(String ime) {
		for (VirtualnaMasina virtualnaMasina : virtualneMasine) {
			if(virtualnaMasina.getIme().equals(ime)) {
				return virtualnaMasina;
			}
		}
		return null;
	}
	
	public List<VirtualnaMasina> getVirtualneMasine() {
		return virtualneMasine;
	}

	public void setVirtualneMasine(List<VirtualnaMasina> virtualneMasine) {
		this.virtualneMasine = virtualneMasine;
	}

}
