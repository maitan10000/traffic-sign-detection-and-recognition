package sample.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import sample.dto.TrafficInfoDTO;

public class SearchByCateID {
	public ArrayList<TrafficInfoDTO> searchByCateID(Connection connection, String cateID) throws Exception{
		ArrayList<TrafficInfoDTO> cateData = new ArrayList<TrafficInfoDTO>();
		try{
			PreparedStatement stm = connection.prepareStatement("SELECT trafficID,name,image,information,penaltyfee FROM trafficinformation WHERE categoryID = ?");
			stm.setString(1, cateID);
			ResultSet rs = stm.executeQuery();
			while(rs.next()){
				TrafficInfoDTO trafficObj = new TrafficInfoDTO();
				trafficObj.setTrafficID(rs.getString("trafficID"));
				trafficObj.setName(rs.getString("name"));
				trafficObj.setImage(rs.getString("image"));
				trafficObj.setInformation(rs.getString("information"));
				trafficObj.setPenaltyfee(rs.getString("penaltyfee"));
				
				cateData.add(trafficObj);
			}
			return cateData;
		}catch(Exception ex){
			throw ex;
		}
		
		
	}
}
