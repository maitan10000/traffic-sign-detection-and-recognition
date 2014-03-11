package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.FavoriteDTO;

public class FavoriteDAOImpl implements FavoriteDAO {

	public int add(FavoriteDTO favorite) {
		try {			
			String creator = favorite.getCreator();
			String trafficID = favorite.getTrafficID();
			java.util.Date utilDate = new java.util.Date();
			Date createDate = new Date(utilDate.getTime());			
			Connection connection = BaseDAO.getConnect();
			PreparedStatement stm = connection.prepareStatement("SELECT * FROM favorite WHERE creator = ? AND trafficID = ?");
			stm.setString(1, creator);
			stm.setString(2, trafficID);		
			int result =  stm.executeUpdate();
			if(result == 0){
				PreparedStatement ps = connection
						.prepareStatement("INSERT INTO favorite(creator,trafficID,createDate,isActive) VALUE (?,?,?,?)");
				ps.setString(1, creator);
				ps.setString(2, trafficID);
				ps.setDate(3, createDate);
				ps.setBoolean(4, true);
				ps.executeUpdate();				
			}else{
				PreparedStatement ps2 = connection.prepareStatement("UPDATE favorite SET isActive = ? WHERE creator = ? AND trafficID = ?");
				ps2.setBoolean(1, true);
				ps2.setString(2, creator);
				ps2.setString(3, trafficID);
				ps2.executeUpdate();
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
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

	public ArrayList<FavoriteDTO> listFavorite(String creator) {
		ArrayList<FavoriteDTO> favoriteData = new ArrayList<FavoriteDTO>();
		try {
			Connection connection = BaseDAO.getConnect();
			PreparedStatement ps = connection
					.prepareStatement("SELECT * FROM favorite where creator = ?");
			ps.setString(1, creator);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				FavoriteDTO favoriteObj = new FavoriteDTO();
				favoriteObj.setTrafficID(rs.getString("trafficID"));
				favoriteObj.setCreateDate(rs.getDate("createDate"));
				favoriteObj.setModifyDate(rs.getDate("modifyDate"));
				favoriteObj.setIsActive(rs.getBoolean("isActive"));
				favoriteData.add(favoriteObj);
			}
			return favoriteData;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
}
