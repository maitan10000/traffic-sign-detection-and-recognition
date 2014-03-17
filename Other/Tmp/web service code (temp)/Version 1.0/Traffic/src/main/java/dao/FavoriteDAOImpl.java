package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.FavoriteDTO;
import dto.FavoriteJSON;

public class FavoriteDAOImpl implements FavoriteDAO {

	public boolean add(FavoriteDTO favorite) {
		Connection connection = null;
		PreparedStatement stm = null;

		try {
			String creator = favorite.getCreator();
			String trafficID = favorite.getTrafficID();
			java.util.Date utilDate = new java.util.Date();
			Date createDate = new Date(utilDate.getTime());
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("SELECT * FROM trafficdb.favorite WHERE creator = ? AND trafficID = ?");
			stm.setString(1, creator);
			stm.setString(2, trafficID);
			ResultSet result = stm.executeQuery();
			if (!result.next()) {
				stm.close();
				stm = connection
						.prepareStatement("INSERT INTO favorite(creator,trafficID,createDate,isActive) VALUE (?,?,?,?)");
				stm.setString(1, creator);
				stm.setString(2, trafficID);
				stm.setDate(3, createDate);
				stm.setBoolean(4, true);
				return stm.executeUpdate() > 0;
			} else {
				stm.close();
				stm = connection
						.prepareStatement("UPDATE trafficdb.favorite SET isActive = ?, modifyDate = NOW() WHERE creator = ? AND trafficID = ?");
				stm.setBoolean(1, true);
				stm.setString(2, creator);
				stm.setString(3, trafficID);
				stm.executeUpdate();
				return stm.executeUpdate() > 0;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (stm != null) {
				try {
					stm.close();
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
		return false;
	}

	public boolean delete(FavoriteDTO favorite) {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			String creator = favorite.getCreator();
			String trafficID = favorite.getTrafficID();
			connection = BaseDAO.getConnect();
			stm = connection
					.prepareStatement("UPDATE trafficdb.favorite SET isActive = ? WHERE creator = ? AND trafficID = ?");
			stm.setBoolean(1, false);
			stm.setString(2, creator);
			stm.setString(3, trafficID);
			return stm.executeUpdate() > 0;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (stm != null) {
				try {
					stm.close();
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
		return false;
	}

	/*
	 * List Favorite By Creator
	 * 
	 * @param:
	 * 
	 * @return list of favorite of user
	 * 
	 * @see dao.FavoriteDAO#listFavorite(java.lang.String)
	 */
	public ArrayList<FavoriteDTO> listFavorite(String creator) {
		return this.listFavorite(creator, false);
	}

	/*
	 * List Favorite By Creator
	 * 
	 * @param:
	 * 
	 * @return list of favorite of user
	 * 
	 * @see dao.FavoriteDAO#listFavorite(java.lang.String)
	 */
	public ArrayList<FavoriteDTO> listFavorite(String creator,
			Boolean getInActive) {
		ArrayList<FavoriteDTO> favoriteData = new ArrayList<FavoriteDTO>();
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = BaseDAO.getConnect();
			if (getInActive == false) {
				// get only favorite has isActive = true
				ps = connection
						.prepareStatement("SELECT trafficID from favorite where creator = ? AND isActive=?");
				ps.setString(1, creator);
				ps.setBoolean(2, true);
			} else {
				// get favorite has isActive = true or false
				ps = connection
						.prepareStatement("SELECT trafficID from favorite where creator = ?");
				ps.setString(1, creator);
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				FavoriteDTO favoriteDTO = new FavoriteDTO();
				favoriteDTO.setTrafficID(rs.getString("trafficID"));
				favoriteData.add(favoriteDTO);
			}
			return favoriteData;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
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
		return null;
	}
}
