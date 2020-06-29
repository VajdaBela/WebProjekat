package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import com.fasterxml.jackson.databind.ObjectMapper;

import data.Disk;
import data.Disk.Tip;
import data.Kategorija;
import data.Korisnik;
import data.Korisnik.Uloga;
import data.Organizacija;
import data.VirtualnaMasina;
import dto.DiskDTO;
import dto.KorisnikDTO;
import dto.OrganizacijaDTO;
import dto.VirtualnaMasinaDTO;
import lists.DiskList;
import lists.KategorijaList;
import lists.KorisnikList;
import lists.OrganizacijaList;
import lists.VirtualnaMasinaList;


public class AllLists {
	public static OrganizacijaList organizacije = new OrganizacijaList();
	public static KorisnikList korisnici = new KorisnikList();
	public static KategorijaList kategorije = new KategorijaList();
	public static DiskList diskovi = new DiskList();
	public static VirtualnaMasinaList virtualneMasine = new VirtualnaMasinaList();
	
	public static String pictureFolder;
	public static String dataFolder;
	
	public static ObjectMapper obj = new ObjectMapper();
	
	public static void saveKategorija() {
		try {
			String fileName = "kategorije.txt";
			String fulfileName = dataFolder + File.separator + fileName;
			PrintWriter out  = new PrintWriter(new FileWriter(fulfileName));
			for (Kategorija kategorija : kategorije.getKategorije().values()) {
				out.println(obj.writeValueAsString(kategorija));
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("_________________WARNING_________________");
			System.out.println("Kategorije file wasnt saved!");
		}
	}
	
	public static void saveKorisnik() {
		try {
			String fileName = "korisnici.txt";
			String fulfileName = dataFolder + File.separator + fileName;
			PrintWriter out  = new PrintWriter(new FileWriter(fulfileName));
			for (Korisnik korisnik : korisnici.getKorisnici().values()) {
				if(korisnik.getUloga() ==  Uloga.SUPER_ADMIN)
					continue;
				KorisnikDTO korisnikDTO = new KorisnikDTO(korisnik, true);
				out.println(obj.writeValueAsString(korisnikDTO));
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("_________________WARNING_________________");
			System.out.println("Kategorije file wasnt saved!");
		}
	}
	
	public static void saveOrganizacija() {
		try {
			String fileName = "organizacije.txt";
			String fulfileName = dataFolder + File.separator + fileName;
			PrintWriter out  = new PrintWriter(new FileWriter(fulfileName));
			for (Organizacija organizacija : organizacije.getOrganizacije().values()) {
				OrganizacijaDTO organizacijaDTO = new OrganizacijaDTO(organizacija);
				out.println(obj.writeValueAsString(organizacijaDTO));
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("_________________WARNING_________________");
			System.out.println("Kategorije file wasnt saved!");
		}
	}
	
	public static void saveVirtualnaMasina() {
		try {
			String fileName = "virtualneMasine.txt";
			String fulfileName = dataFolder + File.separator + fileName;
			PrintWriter out  = new PrintWriter(new FileWriter(fulfileName));
			for (VirtualnaMasina virtualnaMasina : virtualneMasine.getVirtualneMasine().values()) {
				VirtualnaMasinaDTO virtualnaMasinaDTO = new VirtualnaMasinaDTO(virtualnaMasina);
				out.println(obj.writeValueAsString(virtualnaMasinaDTO));
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("_________________WARNING_________________");
			System.out.println("Kategorije file wasnt saved!");
		}
	}
	
	public static void saveDisk() {
		try {
			String fileName = "diskovi.txt";
			String fulfileName = dataFolder + File.separator + fileName;
			PrintWriter out  = new PrintWriter(new FileWriter(fulfileName));
			for (Disk disk : diskovi.getDiskovi().values()) {
				DiskDTO diskDTO = new DiskDTO(disk);
				out.println(obj.writeValueAsString(diskDTO));
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("_________________WARNING_________________");
			System.out.println("Kategorije file wasnt saved!");
		}
	}
	
	public static void loadData() {
		ObjectMapper obj = new ObjectMapper();
		try {
			//kategorije
			String kaFileName = "kategorije.txt";
			String kaFulfileName = dataFolder + File.separator + kaFileName;
			BufferedReader in = new BufferedReader(new FileReader(kaFulfileName));
			String line = in.readLine();
			while(line != null) {
				Kategorija kategorija = obj.readValue(line, Kategorija.class);
				kategorije.getKategorije().put(kategorija.getIme(), kategorija);
				line = in.readLine();
			}
			in.close();
			
			//organizacije
			String orFileName = "organizacije.txt";
			String orFulfileName = dataFolder + File.separator + orFileName;
			in = new BufferedReader(new FileReader(orFulfileName));
			line = in.readLine();
			while(line != null) {
				OrganizacijaDTO orgDTO = obj.readValue(line, OrganizacijaDTO.class);
				Organizacija org = new Organizacija();
				org.setIme(orgDTO.getIme());
				org.setOpis(orgDTO.getOpis());
				org.setLogo(orgDTO.getLogo());
				organizacije.getOrganizacije().put(org.getIme(), org);
				line = in.readLine();
			}
			in.close();
			
			//korisnik
			String koFileName = "korisnici.txt";
			String koFulfileName = dataFolder + File.separator + koFileName;
			in = new BufferedReader(new FileReader(koFulfileName));
			line = in.readLine();
			while(line != null) {
				KorisnikDTO korisnikDTO = obj.readValue(line, KorisnikDTO.class);
				Korisnik korisnik = new Korisnik();
				korisnik.setEmail(korisnikDTO.getEmail());
				korisnik.setLozinka(korisnikDTO.getLozinka());
				korisnik.setIme(korisnikDTO.getIme());
				korisnik.setPrezime(korisnikDTO.getPrezime());
				korisnik.setOrganizacija(organizacije.getOrganizacije().get(korisnikDTO.getOrganizacija()));
				korisnik.getOrganizacija().addKorisnik(korisnik);
				korisnik.setUloga(Uloga.valueOf(korisnikDTO.getUloga()));
				korisnici.getKorisnici().put(korisnik.getEmail(), korisnik);
				line = in.readLine();
			}
			in.close();
			
			//virtualneMasine
			String viFileName = "virtualneMasine.txt";
			String viFulfileName = dataFolder + File.separator + viFileName;
			in = new BufferedReader(new FileReader(viFulfileName));
			line = in.readLine();
			while(line != null) {
				VirtualnaMasinaDTO virmDTO = obj.readValue(line, VirtualnaMasinaDTO.class);
				VirtualnaMasina virm = new VirtualnaMasina();
				virm.setIme(virmDTO.getIme());
				virm.setOrganizacija(organizacije.getOrganizacije().get(virmDTO.getOrganizacija()));
				virm.getOrganizacija().addMasina(virm);
				virm.setKategorija(kategorije.getKategorije().get(virmDTO.getKategorija()));
				virm.setAktivnosti(virmDTO.getAktivnosti());
				virtualneMasine.getVirtualneMasine().put(virm.getIme(), virm);
				line = in.readLine();
			}
			in.close();
			
			//disk
			String diFileName = "diskovi.txt";
			String diFulfileName = dataFolder + File.separator + diFileName;
			in = new BufferedReader(new FileReader(diFulfileName));
			line = in.readLine();
			while(line != null) {
				DiskDTO diskDTO = obj.readValue(line, DiskDTO.class);
				Disk disk = new Disk();
				disk.setIme(diskDTO.getIme());
				disk.setOrganizacija(organizacije.getOrganizacije().get(diskDTO.getOrganizacija()));
				disk.getOrganizacija().addDisk(disk);
				disk.setTip(Tip.valueOf(diskDTO.getTip()));
				disk.setKapacitet(diskDTO.getKapacitet());
				disk.setVirtualanaMasina(virtualneMasine.getVirtualneMasine().get(diskDTO.getVirtualnaMasina()));
				disk.getVirtualanaMasina().addDisk(disk);
				diskovi.getDiskovi().put(disk.getIme(), disk);
				line = in.readLine();
			}
			in.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("_________________WARNING_________________");
			System.out.println("Files werent read!");
		}
	}
}
