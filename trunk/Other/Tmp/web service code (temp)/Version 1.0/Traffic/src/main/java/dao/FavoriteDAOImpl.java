package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.FavoriteDTO;
//import dto.FavoriteJSON;

public class FavoriteDAOImpl implements FavoriteDAO {

	public int add(FavoriteDTO favorite) {
		Connection connection = null;
		PreparedStatement stm = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		try {
			String creator = favorite.getCreator();
			String trafficID = favorite.getTrafficID();
			java.util.Date utilDate = new java.util.Date();
			Date createDate = new Date(utilDate.getTime());
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("SELECT * FROM favorite WHERE creator = ? AND trafficID = ?");
			stm.setString(1, creator);
			stm.setString(2, trafficID);
			ResultSet result = stm.executeQuery();
			if (!result.next()) {
				ps = connection
						.prepareStatement("INSERT INTO favorite(creator,trafficID,createDate,isActive) VALUE (?,?,?,?)");
				ps.setString(1, creator);
				ps.setString(2, trafficID);
				ps.setDate(3, createDate);
				ps.setBoolean(4, true);
				return ps.executeUpdate();
			} else {
				ps2 = connection
						.prepareStatement("UPDATE favorite SET isActive = ? WHERE creator = ? AND trafficID = ?");
				ps2.setBoolean(1, true);
				ps2.setString(2, creator);
				ps2.setString(3, trafficID);
				ps2.executeUpdate();
				return ps2.executeUpdate();						
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			if (stm != null) {
				try {
					stm.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (ps2 != null) {
				try {
					ps2.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return -1;
	}

	public boolean delete(FavoriteDTO favorite) {
		try {
			String creator = favorite.getCreator();
			String trafficID = favorite.getTrafficID();
			Connection connection = BaseDAO.getConnect();
			PreparedStatement ps = connection
					.prepareStatement("UPDATE favorite SET isActive = ? WHERE creator = ? AND trafficID = ?");
			ps.setBoolean(1, false);
			ps.setString(2, creator);
			ps.setString(3, trafficID);
			ps.executeUpdate();
		} catch (Exception ex) {

		}
		return false;
	}

//	public ArrayList<FavoriteJSON> listFavorite(String creator) {
//		ArrayList<FavoriteJSON> favoriteData = new ArrayList<FavoriteJSON>();
//		try {
//			Connection connection = BaseDAO.getConnect();
//			PreparedStatement ps = connection
//					.prepareStatement("SELECT fa.trafficID,t.name, t.image from favorite as fa, trafficinformation as t where fa.creator = ? AND fa.trafficID = t.trafficID AND fa.isActive=?");
//			ps.setString(1, creator);
//			ps.setBoolean(2, true);
//			ResultSet rs = ps.executeQuery();
//			while (rs.next()) {
//				FavoriteJSON favoriteObj = new FavoriteJSON();
//				favoriteObj.setTrafficID(rs.getString("trafficID"));
//				favoriteObj.setName(rs.getString("name"));
//				favoriteObj.setImage(rs.getString("image"));
//				favoriteData.add(favoriteObj);
//			}
//			return favoriteData;
//
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//
//		return null;
//	}
}
