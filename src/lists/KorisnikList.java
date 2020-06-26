package lists;

import java.util.ArrayList;
import java.util.List;

import data.Korisnik;
import data.Organizacija;

public class KorisnikList {
	private List<Korisnik> korisnici = new ArrayList<>();
	
	public List<Korisnik> getByOrganization(Organizacija organization) {
		if(organization == null) {
			return korisnici;
		}
		List<Korisnik> sameOrgKorisnici = new ArrayList<>();
		for (Korisnik korisnik : korisnici) {
			if(korisnik.getOrganizacija() == organization ) {
				sameOrgKorisnici.add(korisnik);
			}
		}
		return sameOrgKorisnici;
	}

	public List<Korisnik> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(List<Korisnik> korisnici) {
		this.korisnici = korisnici;
	}
	
}
