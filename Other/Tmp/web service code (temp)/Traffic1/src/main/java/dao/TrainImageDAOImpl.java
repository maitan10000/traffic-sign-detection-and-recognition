package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TrainImageDAOImpl implements TrainImageDAO {

	public String getTrainInfoID(String imageName) {
		try {
			Connection connection = BaseDAO.getConnect();
			PreparedStatement stm = connection
					.prepareStatement("SELECT trafficID FROM trainimage WHERE imageName = ?");
			stm.setString(1, imageName);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				return rs.getString("trafficID");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
