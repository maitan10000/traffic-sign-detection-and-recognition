package sample.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import sample.DTO.CategoryDTO;
import sample.DTO.TrafficDTO;

public class SearchByCateID {
	public TrafficDTO[] searchByCateID(String id){
		Connection con = null;
	     try{
	         String userName = "root";
	         String pass = "123";
	         String url = "jdbc:mysql://127.0.0.1:3306/cpdb";
	         Class.forName("com.mysql.jdbc.Driver").newInstance();
	         con = DriverManager.getConnection(url, userName, pass);	         
	         String sql = "Select * from cpdb.trafficinformation where trafficID = ?";
	         PreparedStatement stm = con.prepareStatement(sql);	         
	         ResultSet rs = stm.executeQuery();
	         ArrayList<TrafficDTO> list = new ArrayList<TrafficDTO>();
	         while(rs.next()){
	        	 String trafficID = rs.getString("trafficID");
	        	 String name = rs.getString("name");
	        	 String image = rs. getString("image");
	        	 String information = rs.getString("information");
	        	 String pennaltyfee = rs.getString("pennaltyfee");
	        	 String categoryID = rs.getString("categoryID");
	        	 String categoryName = rs.getString("categoryName");
	        	 TrafficDTO traffic = new TrafficDTO(trafficID,name,image,information,pennaltyfee,categoryID,categoryName);
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


