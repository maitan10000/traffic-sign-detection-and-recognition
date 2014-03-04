package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.TrafficInfoDTO;

public class TrafficInfoDAOImpl implements TrafficInfoDAO {

	public ArrayList<TrafficInfoDTO> searchByCateID(int cateID) {
		ArrayList<TrafficInfoDTO> cateData = new ArrayList<TrafficInfoDTO>();
		try {
			Connection connection = BaseDAO.getConnect();
			PreparedStatement stm = connection
					.prepareStatement("SELECT trafficID,name,image FROM trafficinformation WHERE categoryID = ?");
			stm.setInt(1, cateID);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				TrafficInfoDTO trafficObj = new TrafficInfoDTO();
				trafficObj.setTrafficID(rs.getString("trafficID"));
				trafficObj.setName(rs.getString("name"));
				trafficObj.setImage(rs.getString("image"));
				cateData.add(trafficObj);
			}
			return cateData;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public ArrayList<TrafficInfoDTO> searchByName(String name) {
		ArrayList<TrafficInfoDTO> trafficData = new ArrayList<TrafficInfoDTO>();
		try {
			Connection connection = BaseDAO.getConnect();
			PreparedStatement stm = connection
					.prepareStatement("SELECT trafficID,name,image,information,penaltyfee,categoryID FROM trafficinformation WHERE name LIKE ?");
			stm.setString(1, "%" + name + "%");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				TrafficInfoDTO trafficObj = new TrafficInfoDTO();
				trafficObj.setTrafficID(rs.getString("trafficID"));
				trafficObj.setName(rs.getString("name"));
				trafficObj.setImage(rs.getString("image"));
				trafficObj.setInformation(rs.getString("information"));
				trafficObj.setPenaltyfee(rs.getInt("penaltyfee"));
				trafficObj.setCategoryID(rs.getInt("categoryID"));
				trafficData.add(trafficObj);
			}
			return trafficData;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public TrafficInfoDTO viewDetail(String id) {
		try {
			Connection connection = BaseDAO.getConnect();
			PreparedStatement stm = connection
					.prepareStatement("SELECT trafficID, name,image,information,penaltyfee,categoryID FROM trafficinformation WHERE trafficID = ?");
			stm.setString(1, id);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				TrafficInfoDTO trafficObj = new TrafficInfoDTO();
				trafficObj.setTrafficID(rs.getString("trafficID"));
				trafficObj.setName(rs.getString("name"));
				trafficObj.setImage(rs.getString("image"));
				trafficObj.setInformation(rs.getString("information"));
				trafficObj.setPenaltyfee(rs.getInt("penaltyfee"));
				trafficObj.setCategoryID(rs.getInt("categoryID"));

				return trafficObj;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
