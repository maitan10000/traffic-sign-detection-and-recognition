package sample.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import sample.DTO.TrafficDTO;
import sample.utility.DBConnect;

public class ViewDetailTrafficSign {
	public TrafficDTO[] searchByCateID(String id){
		Connection con = null;
	     try{
	    	 con = DBConnect.DBConnect();  	         
	         String sql = "Select * from cpdb.trafficinformation where trafficID = ?";
	         PreparedStatement stm = con.prepareStatement(sql);	  
	         stm.setString(1,"%" + id + "%");
	         ResultSet rs = stm.executeQuery();
	         ArrayList<TrafficDTO> list = new ArrayList<TrafficDTO>();
	         while(rs.next()){
	        	 String trafficID = rs.getString("trafficID");
	        	 String name = rs.getString("name");
	        	 String image = rs. getString("image");
	        	 String information = rs.getString("information");
	        	 String pennaltyfee = rs.getString("pennaltyfee");	        	
	        	 TrafficDTO traffic = new TrafficDTO(trafficID,name,image,information,pennaltyfee);
	        	 list.add(traffic);
	         }
	         TrafficDTO[] result = new TrafficDTO[list.size()];
	         list.toArray(result);
	         return result;

	     }catch( Exception e){
	         e.printStackTrace();
	     }
		return null;

		
	}
		
}
