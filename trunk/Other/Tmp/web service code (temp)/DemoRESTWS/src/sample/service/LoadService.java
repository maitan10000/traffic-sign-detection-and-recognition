//package sample.service;
//
//import java.util.ArrayList;
//
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//
//import sample.dto.CategoryDTO;
//import sample.model.*;
//import sample.tranformer.TranformerJSON;
//
//@Path("/Webservice")
//public class LoadService {
//	@GET
//	@Path("/GetResult")
//	@Produces("application/json")
//	public String messageFeed()
//	{
//	String result = null;
//	try 
//	{
//	ArrayList<CategoryDTO> cateData = null;
//	ProjectManager projectManager= new ProjectManager();
//	cateData = projectManager.load();
//	result = TranformerJSON.Cate(cateData);
//	
//	}
//	catch (Exception e)
//	{
//	System.out.println("Exception Error"); //Console 
//	}
//	return result;
//	}
//}
