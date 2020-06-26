package services;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.WebServiceContext;

import app.AllLists;

@Path("/demo")
public class TestService {

	@Resource
	private WebServiceContext context;

	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public String test() {
		AllLists.kategorije.getKategorije();
		return "REST rulez!";
	}
}
