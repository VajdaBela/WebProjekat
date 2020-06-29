package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import data.Disk;
import data.Disk.Tip;
import data.Kategorija;
import data.Korisnik;
import data.Korisnik.Uloga;
import data.Organizacija;
import data.VirtualnaMasina;

@WebListener
public class Startup implements ServletContextListener {

	@Override
	public void contextInitialized(final ServletContextEvent servletContextEvent) {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			BufferedReader in = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream("conf.txt")));

			AllLists.pictureFolder = in.readLine();
			AllLists.dataFolder = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("_________________WARNING_________________");
			System.out.println("configuration file is bad!");
		}
		
//		makeNewCleanFile();
		
		AllLists.loadData();
	}

	@Override
	public void contextDestroyed(final ServletContextEvent servletContextEvent) {
		// Context shutdown
	}
	
	private void makeNewCleanFile() {
		Organizacija organizacija1 = new Organizacija("org1", "jako dobar");
		Organizacija organizacija2 = new Organizacija("org2", "vrlo los");
		AllLists.organizacije.getOrganizacije().put(organizacija1.getIme(), organizacija1);
		AllLists.organizacije.getOrganizacije().put(organizacija2.getIme(), organizacija2);

		Korisnik korisnik1 = new Korisnik("admin", "admin", "admin", "admin", null, Uloga.SUPER_ADMIN);
		Korisnik korisnik2 = new Korisnik("korisnik2", "korisnik2", "korisnik2", "korisnik2", organizacija1,Uloga.ADMIN);
		Korisnik korisnik3 = new Korisnik("korisnik3", "korisnik3", "korisnik3", "korisnik3", organizacija1,Uloga.KORISNIK);
		Korisnik korisnik4 = new Korisnik("korisnik1", "korisnik1", "korisnik1", "korisnik1", organizacija2,Uloga.KORISNIK);
		AllLists.korisnici.getKorisnici().put(korisnik1.getEmail(), korisnik1);
		AllLists.korisnici.getKorisnici().put(korisnik2.getEmail(), korisnik2);
		AllLists.korisnici.getKorisnici().put(korisnik3.getEmail(), korisnik3);
		AllLists.korisnici.getKorisnici().put(korisnik4.getEmail(), korisnik4);

		Kategorija kategorija1 = new Kategorija("kategorija1", 1, 1, 1);
		Kategorija kategorija2 = new Kategorija("kategorija2", 2, 2, 2);
		Kategorija kategorija3 = new Kategorija("kategorija3", 3, 3, 3);
		AllLists.kategorije.getKategorije().put(kategorija1.getIme(), kategorija1);
		AllLists.kategorije.getKategorije().put(kategorija2.getIme(), kategorija2);
		AllLists.kategorije.getKategorije().put(kategorija3.getIme(), kategorija3);

		VirtualnaMasina virtualnaMasina1 = new VirtualnaMasina("virtualnaMasina1", organizacija1, kategorija1);
		VirtualnaMasina virtualnaMasina2 = new VirtualnaMasina("virtualnaMasina2", organizacija1, kategorija2);
		VirtualnaMasina virtualnaMasina3 = new VirtualnaMasina("virtualnaMasina3", organizacija2, kategorija2);
		AllLists.virtualneMasine.getVirtualneMasine().put(virtualnaMasina1.getIme(), virtualnaMasina1);
		AllLists.virtualneMasine.getVirtualneMasine().put(virtualnaMasina2.getIme(), virtualnaMasina2);
		AllLists.virtualneMasine.getVirtualneMasine().put(virtualnaMasina3.getIme(), virtualnaMasina3);

		Disk disk1 = new Disk("disk1", Tip.HDD, 1, organizacija1, virtualnaMasina1);
		Disk disk2 = new Disk("disk2", Tip.HDD, 2, organizacija1, virtualnaMasina2);
		Disk disk3 = new Disk("disk3", Tip.HDD, 3, organizacija2, virtualnaMasina3);
		AllLists.diskovi.getDiskovi().put(disk1.getIme(), disk1);
		AllLists.diskovi.getDiskovi().put(disk2.getIme(), disk2);
		AllLists.diskovi.getDiskovi().put(disk3.getIme(), disk3);

		korisnik2.getOrganizacija().addKorisnik(korisnik2);
		korisnik3.getOrganizacija().addKorisnik(korisnik3);
		korisnik4.getOrganizacija().addKorisnik(korisnik4);

		virtualnaMasina1.getOrganizacija().addMasina(virtualnaMasina1);
		virtualnaMasina2.getOrganizacija().addMasina(virtualnaMasina2);
		virtualnaMasina3.getOrganizacija().addMasina(virtualnaMasina3);

		disk1.getOrganizacija().addDisk(disk1);
		disk2.getOrganizacija().addDisk(disk2);
		disk3.getOrganizacija().addDisk(disk3);

		disk1.getVirtualanaMasina().addDisk(disk1);
		disk2.getVirtualanaMasina().addDisk(disk2);
		disk3.getVirtualanaMasina().addDisk(disk3);
		
		AllLists.saveKategorija();
		AllLists.saveKorisnik();
		AllLists.saveOrganizacija();
		AllLists.saveVirtualnaMasina();
		AllLists.saveDisk();
	}
}
