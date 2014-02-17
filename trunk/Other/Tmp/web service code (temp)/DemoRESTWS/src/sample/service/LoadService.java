package sample.service;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import sample.dto.CategoryDTO;
import sample.dto.TrafficInfoDTO;
import sample.model.*;
import sample.tranformer.TrafficInfoTrans;
import sample.tranformer.TranformerJSON;

@Path("/Webservice")
public class LoadService {
	
	//Method Webservice
	// Method get list Category	
	@GET
	@Path("/GetResult")
	@Produces("application/json")
	public String messageFeed()
	{
	String result = null;
	try 
	{
	ArrayList<CategoryDTO> cateData = null;
	ProjectManager projectManager= new ProjectManager();
	cateData = projectManager.load();
	result = TranformerJSON.Cate(cateData);
	
	}
	catch (Exception e)
	{
	System.out.println("Exception Error"); //Console 
	}
	return result;
	}
	
	
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
	
	
	@GET
	@Path("/SearchbyName")
	@Produces("application/json")
	public String trafficName(@QueryParam("name") String name)
	{
	String result = null;
	try 
	{
	ArrayList<TrafficInfoDTO> trafficData = null;
	SearchManager smn = new SearchManager();
	trafficData = smn.searchByName(name);
	result = TrafficInfoTrans.Traffic(trafficData);		
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return result;
	}
	
	
	@GET
	@Path("/ViewDetail")
	@Produces("application/json")
	public String viewDetal(@QueryParam("id") String trafficID)
	{
	String result = null;
	try 
	{
	ArrayList<TrafficInfoDTO> trafficData = null;
	SearchManager smn = new SearchManager();
	trafficData = smn.viewDetail(trafficID);
	result = TrafficInfoTrans.Traffic(trafficData);		
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return result;
	}
}
