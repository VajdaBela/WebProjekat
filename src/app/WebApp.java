package app;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import data.Disk;
import data.Kategorija;
import data.Korisnik;
import data.Korisnik.Uloga;
import data.Organizacija;
import data.VirtualnaMasina;
import data.Disk.Tip;

@ApplicationPath("/rest")
public class WebApp extends Application {
	public WebApp() {
		Organizacija organizacija1 = new Organizacija("org1", "jako dobar");
		Organizacija organizacija2 = new Organizacija("org2", "vrlo los");
		AllLists.organizacije.getOrganizacije().add(organizacija1);
		AllLists.organizacije.getOrganizacije().add(organizacija2);
		
		
		Korisnik korisnik1 = new Korisnik("admin", "admin", "admin", "admin", null, Uloga.SUPER_ADMIN);
		Korisnik korisnik2 = new Korisnik("korisnik2", "korisnik2", "korisnik2", "korisnik2", organizacija1, Uloga.ADMIN);
		Korisnik korisnik3 = new Korisnik("korisnik3", "korisnik3", "korisnik3", "korisnik3", organizacija1, Uloga.KORISNIK);
		Korisnik korisnik4 = new Korisnik("korisnik1", "korisnik1", "korisnik1", "korisnik1", organizacija2, Uloga.KORISNIK);
		AllLists.korisnici.getKorisnici().add(korisnik1);
		AllLists.korisnici.getKorisnici().add(korisnik2);
		AllLists.korisnici.getKorisnici().add(korisnik3);
		AllLists.korisnici.getKorisnici().add(korisnik4);
		
		
		Kategorija kategorija1 = new Kategorija("kategorija1", 1,1,1);
		Kategorija kategorija2 = new Kategorija("kategorija2", 2,2,2);
		Kategorija kategorija3 = new Kategorija("kategorija3", 3,3,3);
		AllLists.kategorije.getKategorije().add(kategorija1);
		AllLists.kategorije.getKategorije().add(kategorija2);
		AllLists.kategorije.getKategorije().add(kategorija3);
		
		
		VirtualnaMasina virtualnaMasina1 = new VirtualnaMasina("virtualnaMasina1", organizacija1, kategorija1);
		VirtualnaMasina virtualnaMasina2 = new VirtualnaMasina("virtualnaMasina2", organizacija1, kategorija2);
		VirtualnaMasina virtualnaMasina3 = new VirtualnaMasina("virtualnaMasina3", organizacija2, kategorija2);
		AllLists.virtualneMasine.getVirtualneMasine().add(virtualnaMasina1);
		AllLists.virtualneMasine.getVirtualneMasine().add(virtualnaMasina2);
		AllLists.virtualneMasine.getVirtualneMasine().add(virtualnaMasina3);
		
		
		Disk disk1 = new Disk("disk1", Tip.HDD, 1, organizacija1, virtualnaMasina1);
		Disk disk2 = new Disk("disk2", Tip.HDD, 2, organizacija1, virtualnaMasina2);
		Disk disk3 = new Disk("disk3", Tip.HDD, 3, organizacija2, virtualnaMasina3);
		AllLists.diskovi.getDiskovi().add(disk1);
		AllLists.diskovi.getDiskovi().add(disk2);
		AllLists.diskovi.getDiskovi().add(disk3);
		
		
		organizacija1.getKorisnici().add(korisnik2);
		organizacija1.getKorisnici().add(korisnik3);
		organizacija2.getKorisnici().add(korisnik4);
		
		organizacija1.getMasine().add(virtualnaMasina1);
		organizacija1.getMasine().add(virtualnaMasina2);
		organizacija2.getMasine().add(virtualnaMasina3);
		
		organizacija1.getDiskovi().add(disk1);
		organizacija1.getDiskovi().add(disk2);
		organizacija2.getDiskovi().add(disk3);
		
		virtualnaMasina1.getDiskovi().add(disk1);
		virtualnaMasina2.getDiskovi().add(disk2);
		virtualnaMasina3.getDiskovi().add(disk3);
	}
}
