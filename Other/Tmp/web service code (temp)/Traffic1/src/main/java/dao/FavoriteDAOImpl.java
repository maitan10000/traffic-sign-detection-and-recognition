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
			PreparedStatement ps = connection
					.prepareStatement("INSERT INTO favorite(creator,trafficID,createDate,isActive) VALUE (?,?,?,?)");
			ps.setString(1, creator);
			ps.setString(2, trafficID);
			ps.setDate(3, createDate);
			ps.setBoolean(4, false);
			ps.executeUpdate();
		} catch (Exception ex) {

		}
		return 0;
	}

	public boolean delete(FavoriteDTO favorite) {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<FavoriteDTO> loadFavorite() {
		ArrayList<FavoriteDTO> favoriteData = new ArrayList<FavoriteDTO>();
		try {
			Connection connection = BaseDAO.getConnect();
			PreparedStatement ps = connection
					.prepareStatement("SELECT * FROM favorite");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				FavoriteDTO favoriteObj = new FavoriteDTO();
				favoriteObj.setCreator(rs.getString("creator"));
				favoriteObj.setTrafficID(rs.getString("trafficID"));
				favoriteObj.setCreateDate(rs.getDate("createDate"));
				favoriteObj.setModifyDate(rs.getDate("modifyDate"));
				favoriteObj.setActive(rs.getBoolean("isActive"));
				favoriteData.add(favoriteObj);
			}
			return favoriteData;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
}
