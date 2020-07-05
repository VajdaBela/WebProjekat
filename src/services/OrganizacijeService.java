package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import app.AllLists;
import app.Proveravator;
import data.Disk;
import data.Disk.Tip;
import data.Korisnik;
import data.Korisnik.Uloga;
import data.Organizacija;
import data.VirtualnaMasina;
import dto.OrganizacijaDTO;

@Path("/organizacije")
public class OrganizacijeService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrganizacije(@Context HttpServletRequest request) {
		if(!Proveravator.proveriUlogu(new Uloga[] {Uloga.SUPER_ADMIN}, request)) {
			return Response
					.status(Status.UNAUTHORIZED)
					.entity("Not logged in or insufficient privileges!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		List<OrganizacijaDTO> organizacijeDto = new ArrayList<>();
		for (Organizacija organizacija : AllLists.organizacije.getOrganizacije().values()) {
			organizacijeDto.add(new OrganizacijaDTO(organizacija));
		}
		return Response
				.ok(organizacijeDto)
				.build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makeOrganizacija(@Context HttpServletRequest request, OrganizacijaDTO org) {
		if(!Proveravator.proveriUlogu(new Uloga[] {Uloga.SUPER_ADMIN}, request)) {
			return Response
					.status(Status.UNAUTHORIZED)
					.entity("Not logged in or insufficient privileges!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		if(!AllLists.organizacije.addOrganizacija(org)) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(AllLists.organizacije.problemMsg)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		return Response
				.ok(org)
				.build();
	}
	
	@GET
	@Path("{ime}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrganizacija(@Context HttpServletRequest request, @PathParam("ime") String ime) {
		if(!Proveravator.proveriUlogu(new Uloga[] {Uloga.SUPER_ADMIN, Uloga.ADMIN}, request)) {
			return Response
					.status(Status.UNAUTHORIZED)
					.entity("Not logged in or insufficient privileges!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		Korisnik korisnik = (Korisnik)request.getSession().getAttribute(LoginService.korisnikAttr);
		if(korisnik.getUloga() == Uloga.ADMIN && !korisnik.getOrganizacija().getIme().equals(ime)) {
			return Response
					.status(Status.UNAUTHORIZED)
					.entity("Not logged in or insufficient privileges!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		Organizacija org = AllLists.organizacije.find(ime);
		if(org == null) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(AllLists.organizacije.problemMsg)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		return Response
				.ok(new OrganizacijaDTO(org))
				.build();
	}
	
	@PUT
	@Path("{ime}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeOrganization(@Context HttpServletRequest request, @PathParam("ime") String ime, OrganizacijaDTO org) {
		if(!Proveravator.proveriUlogu(new Uloga[] {Uloga.SUPER_ADMIN, Uloga.ADMIN}, request)) {
			return Response
					.status(Status.UNAUTHORIZED)
					.entity("Not logged in or insufficient privileges!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		Korisnik korisnik = (Korisnik)request.getSession().getAttribute(LoginService.korisnikAttr);
		if(korisnik.getUloga() == Uloga.ADMIN && !korisnik.getOrganizacija().getIme().equals(ime)) {
			return Response
					.status(Status.UNAUTHORIZED)
					.entity("Not logged in or insufficient privileges!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		if(!AllLists.organizacije.changeOrganizacija(org, ime)) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(AllLists.organizacije.problemMsg)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		return Response
				.ok(org)
				.build();
	}
	
	@GET
	@Path("my")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMyOrganizacija(@Context HttpServletRequest request) {
		if(!Proveravator.proveriUlogu(new Uloga[] {Uloga.ADMIN}, request)) {
			return Response
					.status(Status.UNAUTHORIZED)
					.entity("Not logged in or insufficient privileges!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		Korisnik korisnik = (Korisnik)request.getSession().getAttribute(LoginService.korisnikAttr);
		return Response
				.ok(new OrganizacijaDTO(korisnik.getOrganizacija()))
				.build();
	}
	
	@GET
	@Path("cena")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getCena(@Context HttpServletRequest request, HashMap<String, Date> dates) {
		Korisnik korisnik = (Korisnik)request.getSession().getAttribute(LoginService.korisnikAttr);
		if(!Proveravator.proveriUlogu(new Uloga[] {Uloga.ADMIN}, request)) {
			return Response
					.status(Status.UNAUTHORIZED)
					.entity("Not logged in or insufficient privileges!")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		
		
		HashMap<String, Long> prices = new HashMap<>();
		long begining = dates.get("begin").getTime();
		long end = dates.get("end").getTime();
		
		for(VirtualnaMasina virtualnaMasina : korisnik.getOrganizacija().getMasine().values()) {
			prices.put(virtualnaMasina.getIme(), price(calc(virtualnaMasina, begining, end), virtualnaMasina));
		}
		
		return Response
				.ok(prices)
				.build();
	}
	
	private long price(long milliseconds, VirtualnaMasina vm) {
		double hours = (((milliseconds / 1000.0) / 60.0) / 60.0);
		long sum = 0;
		sum += hours * vm.getKategorija().getBrJezgara() * CENA_JEZGRA_PO_SATU;
		sum += hours * vm.getKategorija().getRam() * CENA_RAM_PO_SATU;
		sum += hours * vm.getKategorija().getGpu() * CENA_GPU_JEZGRA_PO_SATU;
		for(Disk disk : vm.getDiskovi().values()) {
			double temp = hours * disk.getKapacitet();
			if(disk.getTip() == Tip.HDD) {
				sum += temp * CENA_HDD_PO_SATU;
			}
			else
				sum += temp * CENA_SSD_PO_SATU;
		}
		return sum;
	}
	
	private long calc(VirtualnaMasina vm, long begining, long end) {
		long miliSecondSum = 0;
		List<Date> dates = vm.getAktivnosti();
		
		if(dates.size() == 1) {
			if(dates.get(0).getTime() < end)
				return end - dates.get(0).getTime();
			else
				return 0;
		}
		
		int idx = 1;
		while(idx < dates.size()) {
			if(dates.get(idx).getTime() > begining) {
				miliSecondSum += dates.get(idx).getTime() - begining;
				break;
			}
			idx += 2;
		}
		
		idx += 2;
		while(idx < dates.size()) {
			
			if(dates.get(idx).getTime() > end) {
				miliSecondSum += end - dates.get(idx-1).getTime();
			}
			else {
				miliSecondSum += dates.get(idx).getTime() - dates.get(idx - 1).getTime();
			}
			idx += 2;
		}
		
		return miliSecondSum;
	}
	
	private double CENA_JEZGRA_PO_SATU = 25.0/(30.0*24);
	private double CENA_RAM_PO_SATU = 15.0/(30.0*24);
	private double CENA_GPU_JEZGRA_PO_SATU = 1.0/(30.0*24);
	private double CENA_HDD_PO_SATU = 0.1/(30.0*24);
	private double CENA_SSD_PO_SATU = 0.3/(30.0*24);
}
