package sample.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import sample.dto.TrafficInfoDTO;

public class ViewDetailTraffic {
	public ArrayList<TrafficInfoDTO> viewDetail(Connection connection, String id) throws Exception{
		ArrayList<TrafficInfoDTO> trafficData = new ArrayList<TrafficInfoDTO>();
		try{
			PreparedStatement stm = connection.prepareStatement("SELECT name,image,information,penaltyfee,categoryID,categoryName FROM trafficinformation WHERE trafficID = ?");
			stm.setString(1,id);
			ResultSet rs = stm.executeQuery();
			while(rs.next()){
				TrafficInfoDTO trafficObj = new TrafficInfoDTO();				
				trafficObj.setName(rs.getString("name"));
				trafficObj.setImage(rs.getString("image"));
				trafficObj.setInformation(rs.getString("information"));
				trafficObj.setPenaltyfee(rs.getString("penaltyfee"));
				trafficObj.setCategoryID(rs.getString("categoryID"));
				trafficObj.setCategoryName(rs.getString("categoryName"));				
				trafficData.add(trafficObj);
			}
			return trafficData;
		}catch(Exception ex){
			throw ex;
		}
		
		
	}

}
