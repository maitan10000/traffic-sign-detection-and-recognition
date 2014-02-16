package sample.service;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import sample.dto.TrafficInfoDTO;
import sample.model.SearchManager;
import sample.tranformer.TrafficInfoTrans;


@Path("/Webservice")
public class SearchByIDService {	
		@GET
		@Path("/Search")
		@Produces("application/json")
		public String messageFeed(@QueryParam("id") String categoryID)
		{
		String result = null;
		try 
		{
		ArrayList<TrafficInfoDTO> trafficData = null;
		SearchManager smn = new SearchManager();
		trafficData = smn.search(categoryID);
		result = TrafficInfoTrans.Traffic(trafficData);		
		}
		catch (Exception e)
		{
		e.printStackTrace();
		}
		return result;
		}
		}


